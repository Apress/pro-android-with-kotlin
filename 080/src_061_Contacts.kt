...val PROJECTION : Array<String>? = null
val SELECTION : String? = null
val selectionArgs : Array<String>? = null

val contentUri = Uri.withAppendedPath( ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(search))
Log.e("LOG", contentUri.toString())
...