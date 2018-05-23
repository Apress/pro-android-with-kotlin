@RunWith(AndroidJUnit4::class)
class MyContentProviderTest : ProviderTestCase2<MyContentProvider>( MyContentProvider::class.java, "com.example.database.provider.MyContentProvider") {
    
    @Before public override   // "public" necessary!
    fun setUp() {
        context = InstrumentationRegistry.getTargetContext()
        super.setUp()
        
        val mockRslv: ContentResolver = mockContentResolver
        mockRslv.delete(MyContentProvider.CONTENT_URI, "1=1", arrayOf())
    }
    
    @Test
    fun test_inserted() {
        val mockCtx: Context = mockContext
        val mockRslv: ContentResolver = mockContentResolver
        
        // add an entry
        val cv = ContentValues()
        cv.put(MyContentProvider.COLUMN_PRODUCTNAME, "Milk")
        cv.put(MyContentProvider.COLUMN_QUANTITY, 27)
        val newItem = mockRslv.insert( MyContentProvider.CONTENT_URI, cv)
        
        // query all
        val cursor = mockRslv.query( MyContentProvider.CONTENT_URI, null, null, null)
        assertThat(cursor.count, `is`(1))
        
        cursor.moveToFirst()
        val ind = cursor.getColumnIndex( MyContentProvider.COLUMN_PRODUCTNAME)
        assertThat(cursor.getString(ind), `is`("Milk"))
    }
}
