// Set the searchable configuration
val searchManager = getSystemService(SEARCH_SERVICE)
as SearchManager
val searchView = menu.findItem(R.id.action_search).actionView as SearchView
searchView.setSearchableInfo( searchManager.getSearchableInfo(componentName))
// Do not iconify the widget; expand it by default:
searchView.setIconifiedByDefault(false)
