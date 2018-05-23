val msgReadIntent = Intent().apply {
    addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
    setAction("com.myapp.auto.MY_ACTION_MESSAGE_READ")
    putExtra("conversation_id", thisConversationId)
    setPackage("com.myapp.auto")
}.let {
    PendingIntent.getBroadcast(applicationContext, thisConversationId, it, PendingIntent.FLAG_UPDATE_CURRENT)
}

val msgReplyIntent = Intent().apply {
    addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
    setAction("com.myapp.auto.MY_ACTION_MESSAGE_REPLY")
    putExtra("conversation_id", thisConversationId)
    setPackage("com.myapp.auto")
}.let {
    PendingIntent.getBroadcast(applicationContext, thisConversationId, it, PendingIntent.FLAG_UPDATE_CURRENT)
}
