val uri = Settings.System.getUriFor( Settings.System.HAPTIC_FEEDBACK_ENABLED)
Log.e("LOG", uri.toString())

val feedbackEnabled = Settings.System.getInt( contentResolver, Settings.System.HAPTIC_FEEDBACK_ENABLED)
Log.e("LOG", Integer.toString(feedbackEnabled))

Settings.System.putInt(contentResolver, Settings.System.HAPTIC_FEEDBACK_ENABLED, 0)
