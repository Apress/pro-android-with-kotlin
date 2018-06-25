override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_searchable)
    
    // This is the standard way a system search dialog
    // or the search widget communicates the query
    // string:
    if (Intent.ACTION_SEARCH == intent.action) {
        val query = intent.getStringExtra(SearchManager.QUERY)
        
        // Add it to the recents suggestion database
        val suggestions = SearchRecentSuggestions(this, RecentsProvider.AUTHORITY, RecentsProvider.MODE)
        suggestions.saveRecentQuery(q, null)
        
        doMySearch(query)
    }
    
    // More initialization if necessary...
}
