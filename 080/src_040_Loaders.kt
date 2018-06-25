class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<MyData> {
    val LOADER_ID = 42
    var loaded:MyData? = null
    
    // other fields and methods...
    
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<MyData>? {
        Log.e("LOG", "onCreateLoader()")
        return makeLoader()
    }
    
    override fun onLoadFinished(loader: Loader<MyData>?, data: MyData?) {
        Log.e("LOG", "Load finished: " + data)
        loaded = data
        // show on UI or other actions...
    }
    
    override fun onLoaderReset(loader: Loader<MyData>?) {
        Log.e("LOG", "onLoaderReset()")
        loaded = null
        // remove from UI or other actions...
    }
}
