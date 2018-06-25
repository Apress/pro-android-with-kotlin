override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
    Log.e("LOG", "query(): " + uri + " - projection=" + Arrays.toString(projection) + " - selection=" + selection + " - selectionArgs=" + Arrays.toString(selectionArgs) + " - sortOrder=" + sortOrder)
    
    val lps = uri.lastPathSegment // the query
    val qr = uri.encodedQuery     // e.g. "limit=50"
    
    val curs = MatrixCursor(arrayOf( BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_INTENT_DATA ))
    curs.addRow(arrayOf(1, lps + "-Suggestion 1", lps + "-Suggestion 1"))
    curs.addRow(arrayOf(2, lps + "-Suggestion 2", lps + "-Suggestion 2"))
    
    return curs
}
