/**
* A camera session class.
*/
class CameraSession(val handler: Handler, val cameraOpenCloseLock:Semaphore, val cameraCharacteristics:CameraCharacteristics?, val textureView: TextureView, val imageReader: ImageReader, val cameraDevice: CameraDevice, val previewDim: PreviewDimension, val rotation:Int, val activeArraySize: Rect, val zoom: Double = 1.0) {
    companion object {
        val LOG_KEY = "Session"
        
        enum class State {
            STATE_PREVIEW, // Showing camera preview.
            STATE_WAITING_LOCK, // Waiting for the focus to be locked.
            STATE_WAITING_PRECAPTURE, // Waiting for the exposure to be
            // precapture state.
            STATE_WAITING_NON_PRECAPTURE, // Waiting for the exposure state to
            // be something other than precapture
            STATE_PICTURE_TAKEN
            // Picture was taken.
        }
    }
    
    var mState:State = State.STATE_PREVIEW
    inner class MyCaptureCallback : CameraCaptureSession.CaptureCallback() {
        private fun process(result: CaptureResult) {
            if(captSess == null)
            return
            when (mState) {
                State.STATE_PREVIEW -> {
                    // We have nothing to do when the
                    // camera preview is working normally.
                }
                State.STATE_WAITING_LOCK -> {
                    val afState = result.get( CaptureResult.CONTROL_AF_STATE)
                    if (CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED == afState || CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED == afState || CaptureResult.CONTROL_AF_STATE_PASSIVE_FOCUSED == afState) {
                        if(cameraHasAutoExposure) {
                            mState = State.STATE_WAITING_PRECAPTURE
                            runPrecaptureSequence()
                        } else {
                            mState = State.STATE_PICTURE_TAKEN
                            captureStillPicture()
                        }
                    }
                }
                State.STATE_WAITING_PRECAPTURE -> {
                    val aeState = result.get( CaptureResult.CONTROL_AE_STATE)
                    if (aeState == null || aeState == CaptureResult.CONTROL_AE_STATE_PRECAPTURE || aeState == CaptureRequest.CONTROL_AE_STATE_FLASH_REQUIRED) {
                        mState = State.STATE_WAITING_NON_PRECAPTURE
                    }
                }
                State.STATE_WAITING_NON_PRECAPTURE -> {
                    val aeState = result.get( CaptureResult.CONTROL_AE_STATE)
                    if (aeState == null || aeState != CaptureResult.CONTROL_AE_STATE_PRECAPTURE) {
                        mState = State.STATE_PICTURE_TAKEN
                        captureStillPicture()
                    }
                }
                else -> {}
            }
        }
        
        override
        fun onCaptureProgressed( session: CameraCaptureSession, request: CaptureRequest, partialResult: CaptureResult) {
            //...
        }
        
        override
        fun onCaptureCompleted( session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {
            process(result)
        }
    }
    
    var captSess: CameraCaptureSession? = null
    var cameraHasAutoFocus = false
    var cameraHasAutoExposure = false
    val captureCallback = MyCaptureCallback()
    
    private val stillImageTakenConsumers = mutableListOf<() -> Unit>()
    fun addStillImageTakenConsumer(l: () -> Unit) {
        stillImageTakenConsumers.add(l)
    }
    
    /**
    * Creates a new [CameraCaptureSession] for camera
    * preview and taking pictures.
    */
    fun createCameraSession() {
        //Log.e(LOG_KEY,"Starting preview session")
        
        cameraHasAutoFocus = cameraCharacteristics?.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES)?.let {
            it.any{ it == CameraMetadata.CONTROL_AF_MODE_AUTO }
        } ?: false
        
        cameraHasAutoExposure = cameraCharacteristics?.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES)?.let {
            it.any{ it == CameraMetadata.CONTROL_AE_MODE_ON ||
                it == CameraMetadata.CONTROL_AE_MODE_ON_ALWAYS_FLASH ||
                it == CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH ||
                it == CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE }
        } ?: false
        
        try {
            val texture = textureView.getSurfaceTexture()
            // We configure the size of default buffer
            // to be the size of camera preview we want.
            texture.setDefaultBufferSize( previewDim.previewSize!!.width, previewDim!!.previewSize!!.height)
            // This is the output Surface we need to start
            // preview.
            val previewSurface = Surface(texture)
            val takePictureSurface = imageReader.surface
            
            // Here, we create a CameraCaptureSession for
            // both camera preview and taking a picture
            cameraDevice.createCaptureSession(Arrays.asList( previewSurface, takePictureSurface), object : CameraCaptureSession.StateCallback() {
                override
                fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                    // When the session is ready, we
                    // start displaying the preview.
                    captSess = cameraCaptureSession
                    try {
                        val captReq = buildPreviewCaptureRequest()
                        captSess?.setRepeatingRequest(captReq, captureCallback, handler)
                    } catch (e: Exception) {
                        Log.e(LOG_KEY, "Cannot access camera "+ "in onConfigured()", e)
                    }
                }
                override fun onConfigureFailed( cameraCaptureSession: CameraCaptureSession) {
                    Log.e(LOG_KEY, "Camera Configuration Failed")
                }
                override fun onActive( sess: CameraCaptureSession) {
                }
                override fun onCaptureQueueEmpty( sess: CameraCaptureSession) {
                }
                override fun onClosed( sess: CameraCaptureSession) {
                }
                override fun onReady( sess: CameraCaptureSession) {
                }
                override fun onSurfacePrepared( sess: CameraCaptureSession, surface: Surface) {
                }
            }, handler
            )
        } catch (e: Exception) {
            Log.e(LOG_KEY, "Camera access failed", e)
        }
    }
    
    /**
    * Initiate a still image capture.
    */
    fun takePicture() {
        lockFocusOrTakePicture()
    }
    
    fun close() {
        try {
            cameraOpenCloseLock.acquire()
            captSess?.run {
                stopRepeating()
                abortCaptures()
                close()
                captSess = null
            }
            cameraDevice.run {
                close()
            }
        } catch (e: InterruptedException) {
            Log.e(LOG_KEY, "Interrupted while trying to lock " + "camera closing.", e)
        } catch (e: CameraAccessException) {
            Log.e(LOG_KEY, "Camera access exception " + "while closing.", e)
        } finally {
            cameraOpenCloseLock.release()
        }
    }
    
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    
    private fun buildPreviewCaptureRequest(): CaptureRequest {
        val texture = textureView.getSurfaceTexture()
        val surface = Surface(texture)
        
        // We set up a CaptureRequest.Builder with the
        // preview output Surface.
        val reqBuilder = cameraDevice.createCaptureRequest(
        CameraDevice.TEMPLATE_PREVIEW)
        reqBuilder.addTarget(surface)
        
        // Zooming
        val cropRect = calcCropRect()
        reqBuilder.set(
        CaptureRequest.SCALER_CROP_REGION, cropRect)
        
        // Flash off
        reqBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF)
        
        // Continuous autofocus
        reqBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
        return reqBuilder.build()
    }
    
    private fun buildTakePictureCaptureRequest() : CaptureRequest {
        // This is the CaptureRequest.Builder that we use
        // to take a picture.
        val captureBuilder = cameraDevice.createCaptureRequest(
        CameraDevice.TEMPLATE_STILL_CAPTURE)
        captureBuilder.addTarget(imageReader.getSurface())
        
        // Autofocus mode
        captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
        
        // Flash auto
        captureBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
        // captureBuilder.set(CaptureRequest.FLASH_MODE,
        // CameraMetadata.FLASH_MODE_OFF)
        
        // Zoom
        val cropRect = calcCropRect()
        captureBuilder.set(CaptureRequest.SCALER_CROP_REGION, cropRect)
        
        // Orientation
        captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, previewDim.getOrientation(rotation))
        return captureBuilder.build()
    }
    
    private fun buildPreCaptureRequest() : CaptureRequest {
        val surface = imageReader.surface
        val reqBuilder = cameraDevice.createCaptureRequest(
        CameraDevice.TEMPLATE_STILL_CAPTURE)
        reqBuilder.addTarget(surface)
        reqBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_START)
        return reqBuilder.build()
    }
    
    private fun buildLockFocusRequest() : CaptureRequest {
        val surface = imageReader.surface
        val reqBuilder = cameraDevice.createCaptureRequest(
        CameraDevice.TEMPLATE_STILL_CAPTURE)
        reqBuilder.addTarget(surface)
        reqBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START)
        return reqBuilder.build()
    }
    
    private fun buildCancelTriggerRequest() : CaptureRequest {
        val texture = textureView.getSurfaceTexture()
        val surface = Surface(texture)
        val reqBuilder = cameraDevice.createCaptureRequest(
        CameraDevice.TEMPLATE_PREVIEW)
        reqBuilder.addTarget(surface)
        reqBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL)
        return reqBuilder.build()
    }
    
    private fun captureStillPicture() {
        val captureRequest = buildTakePictureCaptureRequest()
        if (captSess != null) {
            try {
                val captureCallback = object : CameraCaptureSession.CaptureCallback() {
                    override fun onCaptureCompleted(
                    session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {
                        //Util.showToast(activity,
                        //"Acquired still image")
                        stillImageTakenConsumers.forEach {
                            it() }
                        unlockFocusAndBackToPreview()
                    }
                }
                captSess?.run {
                    stopRepeating()
                    capture(captureRequest, captureCallback, null)
                }
            } catch (e: Exception) {
                Log.e(LOG_KEY, "Cannot capture picture", e)
            }
        }
    }
    
    private fun lockFocusOrTakePicture() {
        if(cameraHasAutoFocus) {
            captSess?.run {
                try {
                    val captureRequest = buildLockFocusRequest()
                    mState = State.STATE_WAITING_LOCK
                    capture(captureRequest, captureCallback, handler)
                } catch (e: Exception) {
                    Log.e(LOG_KEY, "Cannot lock focus", e)
                }
            }
        } else {
            if(cameraHasAutoExposure) {
                mState = State.STATE_WAITING_PRECAPTURE
                runPrecaptureSequence()
            } else {
                mState = State.STATE_PICTURE_TAKEN
                captureStillPicture()
            }
        }
    }
    
    /**
    * Unlock the focus. This method should be called when
    * still image capture sequence is finished.
    */
    private fun unlockFocusAndBackToPreview() {
        captSess?.run {
            try {
                mState = State.STATE_PREVIEW
                val cancelAfTriggerRequest = buildCancelTriggerRequest()
                val previewRequest = buildPreviewCaptureRequest()
                capture(cancelAfTriggerRequest, captureCallback, handler)
                setRepeatingRequest(previewRequest, captureCallback, handler)
            } catch (e: Exception) {
                Log.e(LOG_KEY, "Cannot go back to preview mode", e)
            }
        }
    }
    
    /**
    * Run the precapture sequence for capturing a still
    * image.
    */
    private fun runPrecaptureSequence() {
        try {
            captSess?.run {
                val captureRequest = buildPreCaptureRequest()
                mState = State.STATE_WAITING_PRECAPTURE
                capture(captureRequest, captureCallback, handler)
            }
        } catch (e: Exception) {
            Log.e(LOG_KEY, "Cannot access camera", e)
        }
    }
    
    private fun calcCropRect(): Rect {
        with(activeArraySize) {
            val cropW = width() / zoom
            val cropH = height() / zoom
            val top = centerY() - (cropH / 2f).toInt()
            val left = centerX() - (cropW / 2f).toInt()
            val right = centerX() + (cropW / 2f).toInt()
            val bottom = centerY() + (cropH / 2f).toInt()
            return Rect(left, top, right, bottom)
        }
    }
}
