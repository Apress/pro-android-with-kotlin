abstract fun insert( Uri uri:Uri, values:ContentValues) : Uri

// You don't have to overwrite this, the default
// implementation correctly iterates over the input
// array and calls insert(...) on each element.
fun bulkInsert( uri:Uri, values:Array<ContentValues>) : Int

abstract fun update( uri:Uri, values:ContentValues, selection:String, selectionArgs:Array<String>) : Int

abstract fun delete( uri:Uri, selection:String, selectionArgs:Array<String>) : Int
