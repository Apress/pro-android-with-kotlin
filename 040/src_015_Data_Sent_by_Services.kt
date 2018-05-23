internal class InHandler(val ctx: Context) : Handler() {
    override
    fun handleMessage(msg: Message) {
        // do something with the message from the service
    }
}

class MainActivity : AppCompatActivity() {
    private var remoteSrvc:Messenger? = null
    private var backData:Messenger? = null
    
    private val myConn = object : ServiceConnection {
        override
        fun onServiceConnected(className: ComponentName, service: IBinder) {
            remoteSrvc = Messenger(service)
            backData = Messenger( InHandler(this@MainActivity))
            
            // establish backchannel
            val msg0 = Message.obtain()
            msg0.replyTo = backData
            remoteSrvc?.send(msg0)
            
            // handle forward (client -> service)
            // connectivity...
        }
        
        override
        fun onServiceDisconnected(clazz: ComponentName) {
            remoteSrvc = null
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // bind to the service, use ID from the manifest!
        val intent = Intent("<PCKG>.START_SERVICE")
        intent.setPackage("<PCKG>")
        val flags = Context.BIND_AUTO_CREATE
        bindService(intent, myConn, flags)
    }
}
