override
fun onRequestPermissionsResult( requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    when (requestCode) {
        cameraPermReturnId -> {
            // If request is canceled, the result
            // arrays are empty. Here we know it just
            // can be one entry
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted
                // act accordingly...
            } else {
                // permission denied
                // act accordingly...
            }
            return
        }
        // Add other 'when' lines to check for other
        // permissions this App might request.
        else -> {
            // Ignore all other requests.
            // Or whatever makes sense to you.
        }
    }
}
