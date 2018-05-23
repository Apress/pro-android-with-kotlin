override
fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    photoFile?.run{
        outState?.putString("imgFile", absolutePath)
    }
}
