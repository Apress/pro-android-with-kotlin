...val INTENT_KEY = "my.result.receiver"
val DATA_KEY = "data.key"
...val myReceiver = intent.getParcelableExtra<ResultReceiver>( INTENT_KEY)
...val bndl = Bundle().apply {
    putString(DATA_KEY, "Hello from called component")
}
myReceiver?.send(42, bndl)
