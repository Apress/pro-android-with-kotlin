class MainActivity : AppCompatActivity() {
    var tv:TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // For example add a text at a certain position
        tv = TextView(this).apply {
            text = "Dynamic"
            x = 37.0f
            y = 100.0f
        }
        fl.addView(tv)
    }
}
