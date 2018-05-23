class MyReceiver : BroadcastReceiver() {
    override
    fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show()
        Log.e("LOG", "Received broadcast")
        Thread.sleep(3000)
        // or real work of course...
        Log.e("LOG", "Broadcast done")
    }
}
