fun showTable(tbl:Uri) {
    Log.e("LOG", "##################################")
    Log.e("LOG", tbl.toString())
    val cursor = contentResolver.query( tbl, null, null, null, null)
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
...showTable(ContactsContract.Contacts.CONTENT_URI)
showTable(ContactsContract.RawContacts.CONTENT_URI)
showTable(ContactsContract.Data.CONTENT_URI)
