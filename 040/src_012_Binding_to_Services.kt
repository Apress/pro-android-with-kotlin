var remoteSrvc:Messenger? = null
private val myConnection = object : ServiceConnection {
    override
    fun onServiceConnected(className: ComponentName, service: IBinder) {
        remoteSrvc = Messenger(service)
    }
    override
    fun onServiceDisconnected(className: ComponentName) {
        remoteSrvc = null
    }
}
