class MyFirebaseMessagingService : FirebaseMessagingService() {
    override
    fun onMessageReceived(remoteMessage: RemoteMessage) {
        // ...
        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            
            // Implement a logic:
            // For long-running tasks (10 seconds or more)
            // use Firebase Job Dispatcher.
            scheduleJob()
            // ...or else handle message within 10 seconds
            // handleNow()
        }
        
        // Message contains a notification payload?
        remoteMessage.notification?.run {
            Log.d(TAG, "Message Notification Body: " + body)
        }
    }
    
    private fun handleNow() {
        Log.e("LOG","handleNow()")
    }
    
    private fun scheduleJob() {
        Log.e("LOG","scheduleJob()")
    }
}
