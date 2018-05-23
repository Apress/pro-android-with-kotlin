val es = Executors.newFixedThreadPool(10)
// ...
val future = es.submit({
    Thread.sleep(2000L)
    println("executor over")
    10
} as ()->Int)
val res:Int = future.get()
