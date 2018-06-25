class RecentsProvider : SearchRecentSuggestionsProvider {
    val AUTHORITY = "com.example.RecentsProvider"
    val MODE = DATABASE_MODE_QUERIES
    
    init {
        setupSuggestions(AUTHORITY, MODE)
    }
}
