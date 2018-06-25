val intent = Intent( Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName())
intent.putExtra(Settings.EXTRA_CHANNEL_ID, myNotificationChannel.getId())
startActivity(intent)
