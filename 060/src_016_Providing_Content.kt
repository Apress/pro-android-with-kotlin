object MyContentContract2 {
    val AUTHORITY = "com.xyz.whatitis"
    val CONTENT_URI = Uri.parse("content://"
    + AUTHORITY)
    val SELECTION_ID_BASED = BaseColumns._ID + " = ? "
    object Items {
        val NAME = "item_name"
        val CONTENT_URI = Uri.withAppendedPath( MyContentContract.CONTENT_URI, "items")
        val CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + MyContentContract.AUTHORITY + ".items"
        val CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + MyContentContract.AUTHORITY + ".items"
    }
}
