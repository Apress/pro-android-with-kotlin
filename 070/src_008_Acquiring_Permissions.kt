val activity = this
val perm = Manifest.permission.CAMERA
val cameraPermReturnId = 7239 // any suitable constant
val permissionCheck = ContextCompat.checkSelfPermission( activity, perm)
if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
    // Should we show an explanation?
    if (ActivityCompat.shouldShowRequestPermissionRationale( activity, perm)) {
        // Show an explanation to the user
        // *asynchronously* -- don't block
        // this thread waiting for the user's
        // response! After the user sees the
        // explanation, try again to request
        // the permission.
        val dialog = AlertDialog.Builder(activity) ....create()
        dialog.show()
    } else {
        // No explanation needed, we can request
        // the permission.
        ActivityCompat.requestPermissions(activity, arrayOf(perm), cameraPermReturnId)
        // cameraPermReturnId is an app-defined
        // int constant. The callback method gets
        // the result of the request.
    }
}
