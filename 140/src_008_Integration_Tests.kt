@RunWith(AndroidJUnit4::class)
class MyIntentServiceTest {
    
    @Test
    fun testIntentService() {
        var serviceVal = 0.0
        
        val ctx = InstrumentationRegistry.getTargetContext()
        val serviceIntent = Intent(ctx, MyIntentService::class.java ).apply {
            `package`= ctx.packageName
            putExtra( MyIntentService.MyResultReceiver.INTENT_KEY, MyIntentService.MyResultReceiver( { d->
                serviceVal = d
            }))
        }
        ctx.startService(serviceIntent)
        
        val tm0 = System.currentTimeMillis() / 1000
        var ok = false
        while(System.currentTimeMillis() / 1000 - tm0
        < 20) {
            if(serviceVal == 1.0) {
                ok = true
                break
            }
            Thread.sleep(1000)
        }
        
        assertThat(ok, `is`(true))
    }
}
