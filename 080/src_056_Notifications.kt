if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    // Create the NotificationChannel, but only
    // on API 26+ only after that it is needed
    val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel(channelId, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT)
    } else {
        throw RuntimeException("Internal error")
    }
    channel.description = "Description"
    // Register the channel with the system
    val notificationManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getSystemService( NotificationManager::class.java)
    } else {
        throw RuntimeException("Internal error")
    }
    notificationManager.createNotificationChannel(channel)
}
