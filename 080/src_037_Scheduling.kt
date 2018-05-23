val gps = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
if(gps == ConnectionResult.SUCCESS) {
    // Create a new dispatcher using the Google Play
    // driver.
    val dispatcher = FirebaseJobDispatcher( GooglePlayDriver(this))
    
    val myJob = dispatcher.newJobBuilder()
    .setService(MyJobService::class.java)
    // the JobService that will be called
    .setTag("my-unique-tag")
    // uniquely identifies the job
    .build()
    
    dispatcher.mustSchedule(myJob)
} else {
    Log.e("LOG", "GooglePlayServices not available: " + GoogleApiAvailability.getInstance().getErrorString(gps))
}
