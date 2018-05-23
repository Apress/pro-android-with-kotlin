class MainActivity : WearableActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        setAmbientEnabled() // Enables Always-on
    }
    
    fun go(v: View) {
        val notificationId = 1
        
        // The channel ID of the notification.
        val id = "my_channel_01"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "My channel"
            val description = "Channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel( id, name, importance)
            mChannel.description = description
            // Register the channel with the system
            val notificationManager = getSystemService( Context.NOTIFICATION_SERVICE)
            as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
        
        // Notification channel ID is ignored for Android
        // 7.1.1 (API level 25) and lower.
        val notificationBuilder = NotificationCompat.Builder(this, id)
        .setSmallIcon(android.R.drawable.ic_media_play)
        .setContentTitle("Title")
        .setContentText("Content Text")
        
        // Get NotificationManager service
        val notificationManager = NotificationManagerCompat.from(this)
        
        // Issue the notification
        notificationManager.notify( notificationId, notificationBuilder.build())
    }
}
