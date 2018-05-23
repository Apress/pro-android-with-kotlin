...val PROJECTION = arrayOf( ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME_PRIMARY, ContactsContract.CommonDataKinds.Email.ADDRESS)

val SELECTION = ContactsContract.CommonDataKinds.Email.ADDRESS
+ " LIKE ? " + "AND "
+ ContactsContract.Data.MIMETYPE + " = '"
+ ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
+ "'"

val selectionArgs = arrayOf("%" + search + "%")

val contentUri = ContactsContract.Data.CONTENT_URI
Log.e("LOG", contentUri.toString())
...