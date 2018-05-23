...lateinit var soundLoadManager:SoundLoadManager
...override
fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    ...soundLoadManager = SoundLoadManager(this)
    with(soundLoadManager) {
        load(R.raw.click)
        // more ...
    }
}

fun go(v: View) {
    Log.e("LOG", "All sounds loaded = " + soundLoadManager.allLoaded())
    val strmId = soundLoadManager.play( R.raw.click, false)
    Log.e("LOG", "Stream ID = " + strmId.toString())
}
