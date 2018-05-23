var videoFile:File? = null
val REQUEST_VIDEO_CAPTURE = 43

fun dispatchRecordVideoIntent() {
    fun createVideoFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US).format(Date())
        val imageFileName = "MP4_" + timeStamp + "_"
        val storageDir = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_MOVIES)
        // To instead tke the App's private space:
        // val storageDir = getExternalFilesDir(
        // Environment.DIRECTORY_MOVIES)
        val image = File.createTempFile( imageFileName, ".mp4", storageDir)
        return image
    }
    
    val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
    if (takeVideoIntent.resolveActivity(packageManager) != null) {
        videoFile = createVideoFile()
        val videoURI = FileProvider.getUriForFile(this, "com.example.autho.fileprovider", videoFile!!)
        Log.e("LOG","Video output URI: ${videoURI}")
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI)
        startActivityForResult( takeVideoIntent, REQUEST_VIDEO_CAPTURE)
    }
}
dispatchRecordVideoIntent()
