override
fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    if ((requestCode and 0xFFFF) == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
        val bmOptions = BitmapFactory.Options()
        BitmapFactory.decodeFile( photoFile?.getAbsolutePath(), bmOptions)?.run {
            imgView.setImageBitmap(this)
        }
    }
}
