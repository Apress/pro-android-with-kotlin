override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    if (requestCode and 0xFFFF == REQUEST_CHECK_STATE) {
        Log.e("LOG", "Back from REQUEST_CHECK_STATE")
        ...}
}
