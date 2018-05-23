internal class IncomingHandler(val ctx: Context) : Handler() {
    override
    fun handleMessage(msg: Message) {
        val s = msg.data.getString("MyString")
        val repl = msg.replyTo
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show()
        Log.e("IncomingHandler", "!!! " + s)
        Log.e("IncomingHandler", "!!! replyTo = " + repl)
        
        // If not null, we can now use the 'repl' to send
        // messages to the client. Of course we can save
        // it elsewhere and use it later as well
        if(repl != null) {
            val thr = Thread( object : Runnable {
                override fun run() {
                    Thread.sleep(3000)
                    val msg = Message.obtain()
                    val bundle = Bundle()
                    bundle.putString("MyString", "A reply message to be sent")
                    msg.data = bundle
                    repl?.send(msg)
                }
            } )
            thr.start()
        }
    }
}
