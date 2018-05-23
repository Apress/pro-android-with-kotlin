class MainActivity : AppCompatActivity() {
    private var bcReceiver:BroadcastReceiver? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ...
        
        bcReceiver = MyReceiver()
        val ifi:IntentFilter = IntentFilter("de.pspaeth.myapp.DO_STH")
        LocalBroadcastManager.getInstance(this).registerReceiver(bcReceiver, ifi)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // ...
        
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bcReceiver)
    }
}
