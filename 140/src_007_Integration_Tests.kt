class MyIntentService() : IntentService("MyIntentService") {
    class MyResultReceiver(val cb: (Double) -> Unit) : ResultReceiver(null) {
        companion object {
            val RESULT_CODE = 42
            val INTENT_KEY = "my.result.receiver"
            val DATA_KEY = "data.key"
        }
        override
        fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            val d = resultData?.get(DATA_KEY) as Double
            cb(d)
        }
    }
    var status = 0.0
    
    override fun onHandleIntent(intent: Intent) {
        val myReceiver = intent.getParcelableExtra<ResultReceiver>( MyResultReceiver.INTENT_KEY)
        for (i in 0..100) {
            Thread.sleep(100)
            val bndl = Bundle().apply {
                putDouble(MyResultReceiver.DATA_KEY, i * 0.01)
            }
            myReceiver.send(MyResultReceiver.RESULT_CODE, bndl)
        }
    }
}
