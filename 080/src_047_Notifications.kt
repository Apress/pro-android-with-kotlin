override fun onReceive(context: Context, intent: Intent) {
    Log.e("LOG", intent.toString())
    val KEY_TEXT_REPLY = "key_text_reply"
    
    val remoteInput = RemoteInput.getResultsFromIntent(intent)
    val txt = remoteInput?.getCharSequence(KEY_TEXT_REPLY)?:"undefined"
    val conversationId = intent.getIntExtra("conversationId",0)
    Log.e("LOG","reply text = " + txt)
    
    
    // Do s.th. with the reply...
    
    
    // Build a new notification, which informs the user
    // that the system handled their interaction with
    // the previous notification.
    val NOTIFICATION_CHANNEL_ID = "1"
    val repliedNotification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
    .setSmallIcon(android.R.drawable.ic_media_play)
    .setContentText("Replied")
    .build()
    
    buildChannel(NOTIFICATION_CHANNEL_ID)
    
    // Issue the new notification.
    val notificationManager = NotificationManagerCompat.from(context)
    notificationManager.notify(conversationId, repliedNotification)
}
