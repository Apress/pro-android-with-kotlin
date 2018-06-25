import com.firebase.jobdispatcher.*

class MyJobService : JobService() {
    var jobThread:Thread? = null
    
    override fun onStopJob(job: JobParameters?): Boolean {
        Log.e("LOG", "onStopJob()")
        jobThread?.interrupt()
        jobThread = null
        return false // this job should not be retried
    }
    
    override fun onStartJob(job: JobParameters): Boolean {
        Log.e("LOG", "onStartJob()")
        
        jobThread?.interrupt()
        jobThread = Thread {
            Log.i("LOG", "started job thread")
            // do job work...
            jobFinished(job, false)
            // instead use true to signal a retry
            jobThread = null
            Log.i("LOG", "finished job thread")
        }
        jobThread?.start()
        
        return true // work is going on in the background
    }
}
