override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_searchable)
    
    // This is the standard way a system search dialog
    // or the search widget communicates the query
    // string:
    if (Intent.ACTION_SEARCH == intent.action) {
        val query = intent.getStringExtra(SearchManager.QUERY)
        doMySearch(query)
    }
    
    // More initialization if necessary...
}
