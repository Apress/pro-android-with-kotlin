val PERMISSION_ID = 42
private fun checkPermission(vararg perm:String) : Boolean {
    val havePermissions = perm.toList().all {
        ContextCompat.checkSelfPermission(this,it) == PackageManager.PERMISSION_GRANTED
    }
    if (!havePermissions) {
        if(perm.toList().any {
            ActivityCompat.shouldShowRequestPermissionRationale(this, it)}
        ) {
            val dialog = AlertDialog.Builder(this)
            .setTitle("Permission")
            .setMessage("Permission needed!")
            .setPositiveButton("OK",{
                id, v ->
                ActivityCompat.requestPermissions( this, perm, PERMISSION_ID)
            })
            .setNegativeButton("No",{
                id, v ->
            })
            .create()
            dialog.show()
        } else {
            ActivityCompat.requestPermissions(this, perm, PERMISSION_ID)
        }
        return false
    }
    return true
}
