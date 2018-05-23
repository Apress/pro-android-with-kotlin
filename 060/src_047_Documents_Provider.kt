override fun onActivityResult(requestCode:Int, resultCode:Int, resultData:Intent) {
    
    // The ACTION_OPEN_DOCUMENT intent was sent with the
    // request code READ_REQUEST_CODE. If the request
    // code seen here doesn't match, it's the
    // response to some other intent, and the code below
    // shouldn't run at all.
    
    if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
        // The document selected shows up in
        // intent.getData()
        val uri = resultData.data
        Log.i("LOG", "Uri: " + uri.toString())
        showImage(uri) // Do s.th. with it
    }
}
