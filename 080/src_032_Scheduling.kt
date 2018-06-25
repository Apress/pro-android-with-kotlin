class MyJob : JobService() {
    var jobThread:Thread? = null
    
    override
    fun onStartJob(params: JobParameters) : Boolean {
        Log.i("LOG", "MyJob: onStartJob() : " + params.jobId)
        
        jobThread?.interrupt()
        jobThread = Thread {
            Log.i("LOG", "started job thread")
            // do job work...
            jobFinished(params, false)
            jobThread = null
            Log.i("LOG", "finished job thread")
        }
        jobThread.start()
        return true
    }
    
    override
    fun onStopJob(params:JobParameters) : Boolean {
        Log.i("LOG", "MyJob: onStopJob()")
        jobThread?.interrupt()
        jobThread = null
        return true
    }
}
