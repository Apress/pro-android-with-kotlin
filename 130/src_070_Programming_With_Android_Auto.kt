val notificationBuilder = NotificationCompat.Builder(applicationContext)
.setSmallIcon(smallIconResourceID)
.setLargeIcon(largeIconBitmap)

notificationBuilder.extend(CarExtender() .setUnreadConversation(unreadConvBuilder.build())  NotificationManagerCompat.from(/*context*/this).run {
    notify(notificationTag, notificationId, notificationBuilder.build())
