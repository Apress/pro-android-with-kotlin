class MainActivity : WearableActivity() {
    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAmbientEnabled() // Enables Always-on
    }
}
