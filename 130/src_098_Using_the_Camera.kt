/**
* Calculates and holds preview dimensions
*/
class PreviewDimension {
    
    companion object {
        val LOG_KEY = "PreviewDimension"
        
        // Max preview width guaranteed by Camera2 API
        val MAX_PREVIEW_WIDTH = 1920
        
        // Max preview height guaranteed by Camera2 API
        val MAX_PREVIEW_HEIGHT = 1080
        
        val ORIENTATIONS = SparseIntArray().apply {
            append(Surface.ROTATION_0, 90);
            append(Surface.ROTATION_90, 0);
            append(Surface.ROTATION_180, 270);
            append(Surface.ROTATION_270, 180);
        }
        
        
        /**
        * Given sizes supported by a camera, choose the
        * smallest one that is at least as large as the
        * respective texture view size, and that is at
        * most as large as the respective max size, and
        * whose aspect ratio matches with the specified
        * value. If such size doesn't exist, choose the
        * largest one that is at most as large as the
        * respective max size, and whose aspect ratio
        * matches with the specified value.
        *
        * @param choices           The list of sizes
        *     that the camera supports for the intended
        *     output class
        * @param textureViewWidth  The width of the
        *     texture view relative to sensor coordinate
        * @param textureViewHeight The height of the
        *     texture view relative to sensor coordinate
        * @param maxWidth          The maximum width
        *     that can be chosen
        * @param maxHeight         The maximum height
        *     that can be chosen
        * @param aspectRatio       The aspect ratio
        * @return The optimal size, or an arbitrary one
        *     if none were big enough
        */
        fun chooseOptimalSize(choices: Array<Size>?, textureViewWidth: Int, textureViewHeight: Int, maxWidth: Int, maxHeight: Int, aspectRatio: Size): Size {
            
            // Collect the supported resolutions that are
            // at least as big as the preview Surface
            val bigEnough = ArrayList<Size>()
            // Collect the supported resolutions that are
            // smaller than the preview Surface
            val notBigEnough = ArrayList<Size>()
            val w = aspectRatio.width
            val h = aspectRatio.height
            choices?.forEach { option ->
                if (option.width <= maxWidth && option.height <= maxHeight && option.height == option.width * h / w) {
                    if (option.width >= textureViewWidth && option.height >= textureViewHeight) {
                        bigEnough.add(option)
                    } else {
                        notBigEnough.add(option)
                    }
                }
            }
            
            // Pick the smallest of those big enough. If
            // there is no one big enough, pick the
            // largest of those not big enough.
            if (bigEnough.size > 0) {
                return Collections.min(bigEnough, CompareSizesByArea())
            } else if (notBigEnough.size > 0) {
                return Collections.max(notBigEnough, CompareSizesByArea())
            } else {
                Log.e(LOG_KEY, "Couldn't find any suitable size")
                return Size(textureViewWidth, textureViewHeight)
            }
        }
        
        /**
        * Compares two sizes based on their areas.
        */
        class CompareSizesByArea : Comparator<Size> {
            override
            fun compare(lhs: Size, rhs: Size): Int {
                // We cast here to ensure the
                // multiplications won't overflow
                return Long.signum(lhs.width.toLong() * lhs.height - rhs.width.toLong() * rhs.height)
            }
        }
    }
    
    internal var rotatedPreviewWidth: Int = 0
    internal var rotatedPreviewHeight: Int = 0
    internal var maxPreviewWidth: Int = 0
    internal var maxPreviewHeight: Int = 0
    internal var sensorOrientation: Int = 0
    internal var previewSize: Size? = null
    
    fun calcPreviewDimension(width: Int, height: Int, activity: Activity, bc: BackfaceCamera) {
        // Find out if we need to swap dimension to get
        // the preview size relative to sensor coordinate.
        val displayRotation = activity.windowManager.defaultDisplay.rotation
        
        sensorOrientation = bc.characteristics!!.get(CameraCharacteristics.SENSOR_ORIENTATION)
        var swappedDimensions = false
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 ->
            if (sensorOrientation == 90 || sensorOrientation == 270) {
                swappedDimensions = true
            }
            Surface.ROTATION_90, Surface.ROTATION_270 ->
            if (sensorOrientation == 0 || sensorOrientation == 180) {
                swappedDimensions = true
            }
            else -> Log.e("LOG", "Display rotation is invalid: " + displayRotation)
        }
        
        val displaySize = Point()
        activity.windowManager.defaultDisplay.getSize(displaySize)
        rotatedPreviewWidth = width
        rotatedPreviewHeight = height
        maxPreviewWidth = displaySize.x
        maxPreviewHeight = displaySize.y
        
        if (swappedDimensions) {
            rotatedPreviewWidth = height
            rotatedPreviewHeight = width
            maxPreviewWidth = displaySize.y
            maxPreviewHeight = displaySize.x
        }
        
        if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
            maxPreviewWidth = MAX_PREVIEW_WIDTH
        }
        
        if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
            maxPreviewHeight = MAX_PREVIEW_HEIGHT
        }
    }
    
    /**
    * Retrieves the JPEG orientation from the specified
    * screen rotation.
    *
    * @param rotation The screen rotation.
    * @return The JPEG orientation
    *       (one of 0, 90, 270, and 360)
    */
    fun getOrientation(rotation: Int): Int {
        // Sensor orientation is 90 for most devices, or
        // 270 for some devices (eg. Nexus 5X). We have
        // to take that into account and rotate JPEG
        // properly. For devices with orientation of 90,
        // we simply return our mapping from ORIENTATIONS.
        // For devices with orientation of 270, we need
        // to rotate the JPEG 180 degrees.
        return (ORIENTATIONS.get(rotation) + sensorOrientation + 270) % 360
    }
    
    fun getTransformationMatrix(activity: Activity, viewWidth: Int, viewHeight: Int): Matrix {
        val matrix = Matrix()
        val rotation = activity.windowManager.defaultDisplay.rotation
        val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())
        val bufferRect = RectF(0f, 0f, previewSize!!.height.toFloat(), previewSize!!.width.toFloat())
        val centerX = viewRect.centerX()
        val centerY = viewRect.centerY()
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset( centerX - bufferRect.centerX(), centerY - bufferRect.centerY())
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL)
            val scale = Math.max( viewHeight.toFloat() / previewSize!!.height, viewWidth.toFloat() / previewSize!!.width)
            matrix.postScale( scale, scale, centerX, centerY)
            matrix.postRotate( (90 * (rotation - 2)).toFloat(), centerX, centerY)
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180f, centerX, centerY)
        }
        return matrix
    }
}
