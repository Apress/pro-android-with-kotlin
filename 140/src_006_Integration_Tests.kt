class MyService : Service() {
    class MyBinder(val servc:MyService) : Binder() {
        fun getService():MyService {
            return servc
        }
    }
    
    private val binder: IBinder = MyBinder(this)
    
    override fun onBind(intent: Intent): IBinder = binder
    
    fun add(a:Int, b:Int) = a + b
}
