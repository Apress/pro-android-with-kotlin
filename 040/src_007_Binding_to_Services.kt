/**
* Class used for binding locally, i.e. in the same App.
*/
class MyBinder(val servc:MyService) : Binder() {
    fun getService():MyService {
        return servc
    }
}

class MyService : Service() {
    // Binder given to clients
    private val binder: IBinder = MyBinder(this)
    
    // Random number generator
    private val generator: Random = Random()
    
    override
    fun onBind(intent: Intent):IBinder  {
        return binder
    }
    
    /** method for clients */
    fun getRandomNumber():Int {
        return generator.nextInt(100)
    }
}
