val remoteInput = RemoteInput.getResultsFromIntent(intent)?.let {
    it.getCharSequence(MY_VOICE_REPLY_KEY)
} ?: ""
