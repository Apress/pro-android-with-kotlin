var handlerThread : HandlerThread? = null
...fun doSomething() {
    handlerThread = handlerThread ?: HandlerThread("MyHandlerThread").apply {
        start()
    }
    val handler = Handler(handlerThread?.looper)
    
    handler.post {
        // do s.th. in background
    }
}
