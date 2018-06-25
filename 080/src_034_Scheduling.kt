val jsched = getSystemService(JobScheduler::class.java)
val JOB_ID : Int = 7766

val service = ComponentName(this, MyJob::class.java)
val builder = JobInfo.Builder(JOB_ID, service)
.setMinimumLatency((1 * 1000).toLong())
// wait at least 1 sec
.setOverrideDeadline((3 * 1000).toLong())
// maximum delay 3 secs

jsched.schedule(builder.build())
