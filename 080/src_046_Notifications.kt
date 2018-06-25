fun directReply(view:View) {
    // Key for the string that's delivered in the
    // action's intent.
    val KEY_TEXT_REPLY = "key_text_reply"
    val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
    .setLabel("Reply label")
    .build()
    
    // Make sure this broadcast receiver exists
    val CONVERSATION_ID = 1
    val messageReplyIntent = Intent(this, MyReceiver2::class.java)
    messageReplyIntent.action = "com.xyz2.MAIN"
    messageReplyIntent.putExtra("conversationId", CONVERSATION_ID)
    
    // Build a PendingIntent for the reply
    // action to trigger.
    val replyPendingIntent = PendingIntent.getBroadcast(applicationContext, CONVERSATION_ID, messageReplyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    
    // Create the reply action and add the remote input.
    val action = NotificationCompat.Action.Builder( ... a resource id for an icon ..., "Reply", replyPendingIntent)
    .addRemoteInput(remoteInput)
    .build()
    
    val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
    .setSmallIcon(... a resource id for an icon ...)
    .setContentTitle("Title")
    .setContentText("Content Content Content ...")
    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    // add a reply action button
    .addAction(action)
    
    buildChannel(NOTIFICATION_CHANNEL_ID)
    
    val notificationManager = NotificationManagerCompat.from(this)
    notificationManager.notify( NOTIFICATION_ID, builder.build())
}
