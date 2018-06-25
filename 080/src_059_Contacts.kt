import android.app.Activity
import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.net.Uri.withAppendedPath


class ContactsLoader(val actv: Activity?, val search:String): LoaderManager.LoaderCallbacks<Cursor> {
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor>? {
        Log.e("LOG", "onCreateLoader()")
        
        val PROJECTION = arrayOf( ContactsContract.Contacts._ID, ContactsContract.Contacts.LOOKUP_KEY, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
        
        val SELECTION = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        + " LIKE ?"
        val selectionArgs = arrayOf("%" + search + "%")
        
        val contentUri = ContactsContract.Contacts.CONTENT_URI
        Log.e("LOG", contentUri.toString())
        
        // Starts the query
        return CursorLoader( actv, contentUri, PROJECTION, SELECTION, selectionArgs, null )
    }
    
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        Log.e("LOG", "Load finished: " + data)
        if(data.moveToFirst()) {
            do {
                Log.e("LOG", "Entry:")
                data.columnNames.forEachIndexed { i, s ->
                    Log.e("LOG", "  " + s + " -> " + data.getString(i))
                }
            } while (data.moveToNext())
        }
        // show on UI or other actions...
    }
    
    override fun onLoaderReset(loader: Loader<Cursor>?) {
        Log.e("LOG", "onLoaderReset()")
        // remove from UI or other actions...
    }
}
