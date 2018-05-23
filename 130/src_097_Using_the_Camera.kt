/**
* Find a backface camera
*/
class BackfaceCamera(context:Context) {
    var cameraId: String? = null
    var characteristics: CameraCharacteristics? = null
    
    init {
        val manager = context.getSystemService( Context.CAMERA_SERVICE) as CameraManager
        try {
            manager.cameraIdList.find {
                manager.getCameraCharacteristics(it).get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK
            }.run {
                cameraId = this
                characteristics = manager.getCameraCharacteristics(this)
            }
        } catch (e: CameraAccessException) {
            Log.e("LOG", "Cannot access camera", e)
        }
    }
}
