private var bcReceiver: BroadcastReceiver? = null

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ...bcReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // do s.th. when receiving...
        }
    }
    val ifi: IntentFilter = IntentFilter("de.pspaeth.myapp.DO_STH")
    registerReceiver(bcReceiver, ifi, "com.xyz.theapp.PERMISSION1", null)
}

override fun onDestroy() {
    super.onDestroy()
    unregisterReceiver(bcReceiver)
}
