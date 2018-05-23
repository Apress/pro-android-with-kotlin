fun makeLoader():Loader<MyData> {
    val res = @SuppressLint("StaticFieldLeak")
    object : AsyncTaskLoader<MyData>(this@MainActivity) {
        val myData: MutableList<String> = ArrayList<String>()
        var initLoaded = false
        
        override fun loadInBackground(): MyData {
            Log.e("LOG", "AsyncTaskLoader.loadInBackground()")
            Log.e("LOG", "Thread: " + Thread.currentThread().toString())
            for (i in 0..9) {
                Log.e("LOG", i.toString())
                myData.add("Item " + i.toString())
                Thread.sleep(1000)
                if (isLoadInBackgroundCanceled)
                throw OperationCanceledException( "Canceled")
            }
            return MyData(myData)
        }
        
        override fun onStartLoading() {
            Log.e("LOG", "AsyncTaskLoader.onStartLoading()")
            super.onStartLoading()
            if (!initLoaded)
            forceLoad()
            initLoaded = true
        }
    }
    return res
}
