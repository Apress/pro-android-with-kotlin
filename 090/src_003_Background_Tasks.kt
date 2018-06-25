var handlerThread : HandlerThread? = null
// or: lateinit var handlerThread : HandlerThread
...fun doSomething() {
    handlerThread = handlerThread ?: HandlerThread("MyHandlerThread").apply {
        start()
    }
    val handler = Handler(handlerThread?.looper)
    
    handler.post {
        // do s.th. in background
    }
}
