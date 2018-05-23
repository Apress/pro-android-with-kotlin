class ExampleAppWidgetConfigure : AppCompatActivity() {
    var awi:Int = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conf)
        
        awi = intent.extras.getInt( AppWidgetManager.EXTRA_APPWIDGET_ID)
        Toast.makeText(this,"" + awi, Toast.LENGTH_LONG).show()
        // do more configuration stuff...
    }
    
    fun goBack(view: View) {
        // just an example...
        val data = Intent()
        data.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, awi)
        setResult(RESULT_OK, data)
        finish()
    }
}
