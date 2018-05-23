var myReceiver:ResultReceiver? = null

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_called)
    ...myReceiver = intent.getParcelableExtra<ResultReceiver>( MyResultReceiver.INTENT_KEY)
}

fun go(v: View) {
    val bndl = Bundle().apply {
        putString(MyResultReceiver.DATA_KEY, "Hello from called component")
    }
    myReceiver?.send(42, bndl) ?: throw IllegalStateException("myReceiver is null")
}
