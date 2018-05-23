val NOTIFICATION_CHANNEL_ID = "1"
val NOTIFICATION_ID = 1

// Make sure this Activity exists
val intent = Intent(this, AlertDetails::class.java)
intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//or Intent.FLAG_ACTIVITY_CLEAR_TASK
val tapIntent = PendingIntent.getActivity(this, 0, intent, 0)

// Make sure this broadcast receiver  exists and can
// be called by explicit Intent like this
val actionIntent = Intent(this, MyReceiver::class.java)
actionIntent.action = "com.xyz.MAIN"
actionIntent.putExtra(EXTRA_NOTIFICATION_ID, 0)
val actionPendingIntent = PendingIntent.getBroadcast(this, 0, actionIntent, 0)

val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
.setSmallIcon( ... an icon resource id... )
.setContentTitle("Title")
.setContentText("Content Content Content Content ...")
.setPriority(NotificationCompat.PRIORITY_DEFAULT)
// add the default tap action
.setContentIntent(tapIntent)
.setAutoCancel(true)
// add a custom action button
.addAction( ... an icon resource id ..., "Go", actionPendingIntent)

buildChannel(NOTIFICATION_CHANNEL_ID)

val notificationManager = NotificationManagerCompat.from(this)
notificationManager.notify( NOTIFICATION_ID, builder.build())
