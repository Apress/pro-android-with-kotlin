val searchStr = "" // or whatever
val ldr = ContactsLoader(this, searchStr)
loaderManager.initLoader(0, null, ldr)
