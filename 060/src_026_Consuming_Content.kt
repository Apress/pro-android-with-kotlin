val havePermissions = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
== PackageManager.PERMISSION_GRANTED
&& ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG)
== PackageManager.PERMISSION_GRANTED
if(!havePermissions) {
    // Acquire permissions...
}else {
    val uri = CallLog.Calls.CONTENT_URI
    val cursor = contentResolver.query( uri, null, null, null, null)
    cursor.moveToFirst()
    while (!cursor.isAfterLast) {
        Log.e("LOG", "New entry:")
        for(name in cursor.columnNames) {
            val v = cursor.getString( cursor.getColumnIndex(name))
            Log.e("LOG","  > " + name + " = " + v)
        }
        cursor.moveToNext()
    }
}
