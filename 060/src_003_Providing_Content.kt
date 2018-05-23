abstract fun query(        // ----- Variant A -----
uri:Uri, projection:Array<String>, selection:String, selectionArgs:Array<String>, sortOrder:String) : Cursor

// ----- Variant B -----
// You don't have to implement this. The default
// implementation calls variant A, but disregards the
// 'cancellationSignal' argument.
fun query( uri:Uri, projection:Array<String>, selection:String, selectionArgs:Array<String>, String sortOrder:String, cancellationSignal:CancellationSignal) : Cursor


// ----- Variant C -----
// You don't have to implement this. The default
// implementation converts the bundle argument to
// appropriate arguments for calling variant B.
// The bundle keys used are:
//     ContentResolver.QUERY_ARG_SQL_SELECTION
//     ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS
//     ContentResolver.QUERY_ARG_SQL_SORT_ORDER -or-
//         ContentResolver.QUERY_ARG_SORT_COLUMNS
//         (this being a String array)
fun query( uri:Uri, projection:Array<String>, queryArgs:Bundle, cancellationSignal:CancellationSignal) : Cursor
