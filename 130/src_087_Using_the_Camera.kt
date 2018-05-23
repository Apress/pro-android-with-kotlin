val REQUEST_TAKE_PHOTO = 42
var photoFile:File? = null
fun dispatchTakePictureIntent() {
    fun createImageFile():File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        
        val storageDir = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES)
        // To instead take the App's private space:
        // val storageDir =
        // getExternalFilesDir(
        // Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile( imageFileName, ".jpg", storageDir)
        return image
    }
    
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    val canHandleIntent = takePictureIntent.resolveActivity(packageManager) != null
    if (canHandleIntent) {
        photoFile = createImageFile()
        Log.e("LOG","Photo output File: ${photoFile}")
        val photoURI = FileProvider.getUriForFile(this, "com.example.autho.fileprovider", photoFile!!)
        Log.e("LOG","Photo output URI: ${photoURI}")
        takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
    }
}
dispatchTakePictureIntent()
