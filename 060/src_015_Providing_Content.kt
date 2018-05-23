class MyContentContract {
    companion object {
        // The authority and the base URI
        @JvmField
        val AUTHORITY = "com.xyz.whatitis"
        @JvmField
        val CONTENT_URI = Uri.parse("content://" +
        AUTHORITY)
        
        // Selection for ID bases query
        @JvmField
        val SELECTION_ID_BASED = BaseColumns._ID + " = ? "
        
    }
    // For various tables (or item types) it is
    // recommended to use inner classes to specify
    // them. This is just an example
    class Items {
        companion object {
            // The name of the item.
            @JvmField
            val NAME = "item_name"
            
            // The content URI for items
            @JvmField
            val CONTENT_URI = Uri.withAppendedPath( MyContentContract.CONTENT_URI, "items")
            // The MIME type of a directory of items
            @JvmField
            val CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + MyContentContract.AUTHORITY + ".items"
            // The mime type of a single item.
            @JvmField
            val CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + MyContentContract.AUTHORITY + ".items"
            
            // You could add database column names or
            // projection specifications here, or sort
            // order specifications, and more
            // ...
        }
    }
}
