import android.database.sqlite.SQLiteDatabase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatcher
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.mockito.BDDMockito.*
import org.mockito.Matchers
import org.powermock.reflect.Whitebox

@RunWith(PowerMockRunner::class)
@PrepareForTest(MainActivity::class)
class MainActivityTest {
    
    @Test
    fun table_created() {
        val activity = MainActivity()
        val activitySpy = spy(activity)
        val db = mock(SQLiteDatabase::class.java)
        
        // given
        given(activitySpy.openOrCreateDatabase( anyString(), anyInt(), any())).willReturn(db)
        
        // when
        Whitebox.invokeMethod<Unit>( activitySpy,"saveInDb","hello")
        
        // then
        verify(db).execSQL(Matchers.argThat( object : ArgumentMatcher<String>() {
            override
            fun matches(arg:Any):Boolean {
                return arg.toString().matches( Regex("(?i)create table.*\\bMyItems\\b.*"))
            }
        }))
        
    }
    
    @Test
    fun item_inserted() {
        val activity = MainActivity()
        val activitySpy = spy(activity)
        val db = mock(SQLiteDatabase::class.java)
        
        // given
        given(activitySpy.openOrCreateDatabase(
        anyString(), anyInt(), any())).willReturn(db)
        
        // when
        Whitebox.invokeMethod<Unit>(
        activitySpy,"saveInDb","hello")
        
        // then
        verify(db).execSQL(Matchers.argThat(
        object : ArgumentMatcher<String>() {
            override
            fun matches(arg:Any):Boolean {
                return arg.toString().matches( Regex("(?i)insert into MyItems\\b.*"))
            }
        }), Matchers.argThat(
        object : ArgumentMatcher<Array<Any>>() {
            override
            fun matches(arg:Any):Boolean {
                val arr = arg as Array<Any>
                return arr[0] == "hello" &&
                arr[1] is Number
            }
        }))
    }
}
