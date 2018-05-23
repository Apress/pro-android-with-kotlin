// Build a RemoteInput for receiving voice input
// in a Car Notification
val remoteInput = RemoteInput.Builder(MY_VOICE_REPLY_KEY)
.setLabel("The label")
.build()

val unreadConvBuilder = UnreadConversation.Builder(conversationName)
.setReadPendingIntent(msgReadIntent)
.setReplyAction(msgReplyIntent, remoteInput)
