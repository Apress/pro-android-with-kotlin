val PEOPLE_DIR_AUTHORITY = "directory"
val PEOPLE = 1
val PEOPLE_ID = 2
val PEOPLE_PHONES = 3
val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
init {
    uriMatcher.addURI(PEOPLE_DIR_AUTHORITY, "people", PEOPLE)
    uriMatcher.addURI(PEOPLE_DIR_AUTHORITY, "people/#", PEOPLE_ID)
    uriMatcher.addURI(PEOPLE_DIR_AUTHORITY, "people/#/phone", PEOPLE_PHONES)
}
