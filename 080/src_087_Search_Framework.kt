class CustomProvider : ContentProvider() {
    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        Log.e("LOG", "query(): " + uri + " - projection=" + Arrays.toString(projection) + " - selection=" + selection + " - selectionArgs=" + Arrays.toString(selectionArgs) + " - sortOrder=" + sortOrder)
        return null
    }
    
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException( "Not yet implemented")
    }
    
    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException( "Not yet implemented")
    }
    
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException( "Not yet implemented")
    }
    
    override fun onCreate(): Boolean {
        return false
    }
    
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException( "Not yet implemented")
    }
}
