if(!Settings.System.canWrite(this)) {
    val intent = Intent( Settings.ACTION_MANAGE_WRITE_SETTINGS)
    intent.data = Uri.parse( "package:" + getPackageName())
    startActivity(intent)
}
