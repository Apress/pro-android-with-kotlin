import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.hamcrest.Matchers.*
import android.content.Intent

@RunWith(AndroidJUnit4::class)
class BroadcastTest {
    @Test
    fun testBroadcastReceiver() {
        val context = InstrumentationRegistry.getTargetContext()
        
        val intent = Intent(context, MyReceiver::class.java)
        intent.putExtra("data", "Hello World!")
        context.sendBroadcast(intent)
        
        // Give the receiver some time to do its work
        Thread.sleep(5000)
        
        // Check the DB for the entry added
        // by the broadcast receiver
        val db = MyDBHandler(context)
        val p = db.findProduct("Milk")
        assertThat(p, isA(Product::class.java))
        assertThat(p!!.productName, `is`("Milk"))
    }
}
