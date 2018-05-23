private fun loadContactPhotoThumbnail(photoData: String): Bitmap? {
    var afd: AssetFileDescriptor? = null
    try {
        val thumbUri = Uri.parse(photoData)
        afd = contentResolver.openAssetFileDescriptor(thumbUri, "r")
        afd?.apply {
            fileDescriptor?.apply {
                return BitmapFactory.decodeFileDescriptor( this, null, null)
            }
        }
    } catch (e: FileNotFoundException) {
        // Handle file not found errors ...
    } finally {
        afd?.close()
    }
    return null
}
