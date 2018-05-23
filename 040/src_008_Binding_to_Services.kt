val servcConn = object : ServiceConnection {
    override
    fun onServiceDisconnected(compName: ComponentName?) {
        Log.e("LOG","onServiceDisconnected: " + compName)
    }
    override
    fun onServiceConnected(compName: ComponentName?, binder: IBinder?) {
        Log.e("LOG","onServiceConnected: " + compName)
        val servc = (binder as MyBinder).getService()
        Log.i("LOG", "Next random number from service: " + servc.getRandomNumber())
    }
    override
    fun onBindingDied(compName:ComponentName) {
        Log.e("LOG","onBindingDied: " + compName)
    }
}
val intent = Intent(this, MyService::class.java)
val flags = BIND_AUTO_CREATE
bindService(intent, servcConn, flags)
