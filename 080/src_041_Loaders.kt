fun go(view: View) {
    loaderManager.initLoader(LOADER_ID,null,this)
}

fun dismiss(view: View) {
    loaderManager.getLoader<MyData>(LOADER_ID)?.cancelLoad()
    loaderManager.destroyLoader(LOADER_ID)
}
