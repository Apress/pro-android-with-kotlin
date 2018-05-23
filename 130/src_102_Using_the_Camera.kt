/**
* A camera with a preview sent to a TextureView
*/
class Camera(val activity: Activity, val previewDim:PreviewDimension, val textureView:TextureView) {
    companion object {
        val LOG_KEY = "camera"
        val STILL_IMAGE_FORMAT = ImageFormat.JPEG
        val STILL_IMAGE_MIN_WIDTH = 480
        val STILL_IMAGE_MIN_HEIGHT = 480
    }
    
    private val previewSizeListeners = mutableListOf<(Int,Int) -> Unit>()
    fun addPreviewSizeListener( l: (Int,Int) -> Unit ) {
        previewSizeListeners.add(l)
    }
    
    private val stillImageConsumers = mutableListOf<(ByteArray) -> Unit>()
    fun addStillImageConsumer( l: (ByteArray) -> Unit) {
        stillImageConsumers.add(l)
    }
    
    /**
    * An additional thread and handler for running
    * tasks that shouldn't block the UI.
    */
    private var mBackgroundThread: HandlerThread? = null
    private var mBackgroundHandler: Handler? = null
    
    private var cameraDevice: CameraDevice? = null
    private val backfaceCamera = BackfaceCamera(activity)
    // Holds the backface camera's ID
    
    /**
    * A [Semaphore] to prevent the app from exiting
    * before closing the camera.
    */
    private val cameraOpenCloseLock = Semaphore(1)
    
    private var imageReader:ImageReader? = null
    
    private var paused = false
    
    private var flashSupported = false
    
    private var activeArraySize: Rect? = null
    
    private var cameraSession:CameraSession? = null
    
    private var stillImageBytes:ByteArray? = null
    
    fun openCamera(width: Int, height: Int) {
        startBackgroundThread()
        
        val permission1 = ContextCompat.checkSelfPermission( activity, Manifest.permission.CAMERA)
        if (permission1 != PackageManager.PERMISSION_GRANTED) {
            Log.e(LOG_KEY, "Internal error: "+ "Camera permission missing")
        }
        
        setUpCameraOutputs(width, height)
        val manager = activity.getSystemService( Context.CAMERA_SERVICE)
        as CameraManager
        try {
            if (!cameraOpenCloseLock.tryAcquire( 2500, TimeUnit.MILLISECONDS)) {
                throw RuntimeException( "Time out waiting.")
            }
            val mStateCallback = object : CameraDevice.StateCallback() {
                override
                fun onOpened(cameraDev: CameraDevice) {
                    // This method is called when the
                    // camera is opened.  We start camera
                    // preview here.
                    cameraOpenCloseLock.release()
                    cameraDevice = cameraDev
                    createCameraSession()
                }
                
                override
                fun onDisconnected( cameraDev: CameraDevice) {
                    cameraOpenCloseLock.release()
                    cameraDevice?.close()
                    cameraDevice = null
                }
                
                override
                fun onError(cameraDev: CameraDevice, error: Int) {
                    Log.e(LOG_KEY, "Camera on error callback: " + error);
                    cameraOpenCloseLock.release()
                    cameraDevice?.close()
                    cameraDevice = null
                }
            }
            
            manager.openCamera( backfaceCamera.cameraId, mStateCallback, mBackgroundHandler)
        } catch (e: CameraAccessException) {
            Log.e(LOG_KEY,"Could not access camera", e)
        } catch (e: InterruptedException) {
            Log.e(LOG_KEY, "Interrupted while camera opening.", e)
        }
    }
    
    /**
    * Initiate a still image capture.
    */
    fun takePicture() {
        cameraSession?.takePicture()
    }
    
    fun close() {
        stopBackgroundThread()
        cameraSession?.run {
            close()
        }
        imageReader?.run {
            surface.release()
            close()
            imageReader = null
        }
    }
    
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    
    /**
    * Starts a background thread and its [Handler].
    */
    private fun startBackgroundThread() {
        mBackgroundThread = HandlerThread("CameraBackground")
        mBackgroundThread?.start()
        mBackgroundHandler = Handler( mBackgroundThread!!.getLooper())
    }
    
    /**
    * Stops the background thread and its [Handler].
    */
    private fun stopBackgroundThread() {
        mBackgroundThread?.run {
            quitSafely()
            try {
                join()
                mBackgroundThread = null
                mBackgroundHandler = null
            } catch (e: InterruptedException) {
            }
        }
    }
    
    private fun createCameraSession() {
        cameraSession = CameraSession(mBackgroundHandler!!, cameraOpenCloseLock, backfaceCamera.characteristics, textureView, imageReader!!, cameraDevice!!, previewDim, activity.windowManager.defaultDisplay.rotation, activeArraySize!!, 1.0).apply {
            createCameraSession()
            addStillImageTakenConsumer {
                //Log.e(LOG_KEY, "!!! PICTURE TAKEN!!!")
                for (cons in stillImageConsumers) {
                    mBackgroundHandler?.post( Runnable {
                        stillImageBytes?.run{
                            cons(this)
                        }
                    })
                }
            }
        }
    }
    
    /**
    * Sets up member variables related to camera:
    * activeArraySize, imageReader, previewDim,
    * flashSupported
    *
    * @param width  The width of available size for
    *       camera preview
    * @param height The height of available size for
    *       camera preview
    */
    private fun setUpCameraOutputs(
    width: Int, height: Int) {
        activeArraySize = backfaceCamera.characteristics?.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE)
        
        val map = backfaceCamera.characteristics!!.get(
        CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
        
        val stillSize = calcStillImageSize(map)
        imageReader = ImageReader.newInstance(
        stillSize.width, stillSize.height, STILL_IMAGE_FORMAT, 3).apply {
            setOnImageAvailableListener(
            ImageReader.OnImageAvailableListener {
                reader ->
                if (paused)
                return@OnImageAvailableListener
                val img = reader.acquireNextImage()
                val buffer = img.planes[0].buffer
                stillImageBytes = ByteArray(buffer.remaining())
                buffer.get(stillImageBytes)
                img.close()
            }, mBackgroundHandler)
        }
        
        previewDim.calcPreviewDimension(width, height, activity, backfaceCamera)
        
        val texOutputSizes = map?.getOutputSizes(
        SurfaceTexture::class.java)
        val optimalSize = PreviewDimension.chooseOptimalSize(
        texOutputSizes, previewDim.rotatedPreviewWidth, previewDim.rotatedPreviewHeight, previewDim.maxPreviewWidth, previewDim.maxPreviewHeight, stillSize)
        previewDim.previewSize = optimalSize
        
        // We fit the aspect ratio of TextureView
        // to the size of preview we picked.
        val orientation = activity.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            previewSizeListeners.forEach{
                it(optimalSize.width, optimalSize.height) }
        } else {
            previewSizeListeners.forEach{
                it(optimalSize.height, optimalSize.width) }
        }
        
        // Check if the flash is supported.
        val available = backfaceCamera.characteristics?.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
        flashSupported = available ?: false
    }
    
    private fun calcStillImageSize(
    map: StreamConfigurationMap): Size {
        // For still image captures, we use the smallest
        // one at least some width x height
        val jpegSizes = map.getOutputSizes(ImageFormat.JPEG)
        var stillSize: Size? = null
        for (s in jpegSizes) {
            if (s.height >= STILL_IMAGE_MIN_HEIGHT
            && s.width >= STILL_IMAGE_MIN_WIDTH) {
                if (stillSize == null) {
                    stillSize = s
                } else {
                    val f = (s.width * s.height).toFloat()
                    val still = (stillSize.width *
                    stillSize.height).toFloat()
                    if (f < still) {
                        stillSize = s
                    }
                }
            }
        }
        return stillSize ?: Size(100,100)
    }
}
