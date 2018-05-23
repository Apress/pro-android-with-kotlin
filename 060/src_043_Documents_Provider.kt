override fun queryChildDocuments( parentDocumentId: String?, projection: Array<out String>?, sortOrder: String?): Cursor {
    val bndl = Bundle()
    bndl.putString( ContentResolver.QUERY_ARG_SQL_SORT_ORDER, sortOrder)
    return queryChildDocuments( parentDocumentId, projection, bndl)
}
