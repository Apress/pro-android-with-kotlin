class RecommendationBuilder( val id:Int = 0, val context:Context, val title:String, val description:String, var priority:Int = 0, val image: Bitmap, val smallIcon: Int = 0, val intent: PendingIntent, val extras:Bundle? = null ) {
    fun build(): Notification {
        val notification:Notification = NotificationCompat.BigPictureStyle( NotificationCompat.Builder(context) .setContentTitle(title) .setContentText(description) .setPriority(priority) .setLocalOnly(true) .setOngoing(true) .setColor(...) .setCategory( Notification.CATEGORY_RECOMMENDATION) .setLargeIcon(image) .setSmallIcon(smallIcon) .setContentIntent(intent) .setExtras(extras))
        .build()
        return notification
    }
}
