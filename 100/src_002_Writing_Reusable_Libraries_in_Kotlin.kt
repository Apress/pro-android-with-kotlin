import org.junit.Assert.*
import org.junit.Test
...
class RegularExpressionTest {
    @Test
    fun proof_of_concept() {
        assertEquals(1, ("Hello Nelo" / ".*el.*").size)
        assertEquals(2, ("Hello Nelo" / ".*?el.*?").size)
        
        var s1:String = ""
        ("Hello Nelo" / "e[lX]").forEach {
            s1 += it.groupValues
        }
        assertEquals("[el][el]", s1)
        
        var s2: String = ""
        ("Hello Nelo" / ".*el.*").firstOrNull()?.run {
            s2 += this[0]
        }
        assertEquals("Hello Nelo", s2)
        
        assertEquals("el", ("Hello Nelo" % ".*(el).*")?.let{ it[1] } )
        
        assertEquals("el", ("Hello Nelo" / ".*(el).*")[0][1])
        
        var match1: Boolean = false
        "Hello".onMatch(".*el.*") {
            match1 = true
        }
        assertTrue(match1)
    }
}
