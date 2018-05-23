class MainActivity : AppCompatActivity() {
    private var bcReceiver:BroadcastReceiver? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ...
        bcReceiver = MyReceiver()
        val ifi:IntentFilter = IntentFilter("de.pspaeth.myapp.DO_STH")
        registerReceiver(bcReceiver, ifi)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // ...
        unregisterReceiver(bcReceiver)
    }
}
