class MyResultReceiver : ResultReceiver(null) {
    companion object {
        val INTENT_KEY = "my.result.receiver"
        val DATA_KEY = "data.key"
    }
    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        super.onReceiveResult(resultCode, resultData)
        val d = resultData?.get(DATA_KEY) as String
        Log.e("LOG", "Received: " + d)
    }
}
