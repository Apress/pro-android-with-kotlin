override
protected fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent) {
    if ((requestCode and 0xFFFF) == backFromSettingPerm) {
        if (resultCode == Activity.RESULT_OK) {
            // act accordingly...
        }
    }
}
