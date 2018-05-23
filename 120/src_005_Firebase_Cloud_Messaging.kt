class MyFirebaseInstanceIdService : FirebaseInstanceIdService() {
    override
    fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)
    }
}
