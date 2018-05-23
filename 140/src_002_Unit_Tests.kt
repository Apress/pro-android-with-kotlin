class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    
    fun save(v: View) {
        saveInDb(et.text.toString())
    }
    
    fun count(v: View) {
        val db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null)
        with(db) {
            val resultSet = rawQuery( "Select * from MyItems", null)
            val cnt = resultSet.count
            Toast.makeText(this@MainActivity, "Count: ${cnt}", Toast.LENGTH_LONG).show()
        }
        db.close()
    }
    
    private fun saveInDb(item:String) {
        val tm = System.currentTimeMillis() / 1000
        val db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null)
        with(db) {
            execSQL("CREATE TABLE IF NOT EXISTS " + "MyItems(Item VARCHAR,timestamp INT);")
            execSQL("INSERT INTO MyItems VALUES(?,?);", arrayOf(item, tm))
        }
        db.close()
    }
}
