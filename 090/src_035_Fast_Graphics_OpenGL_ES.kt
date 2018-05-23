class MyActivity : AppCompatActivity() {
    var glView:GLSurfaceView? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        glView = MyGLSurfaceView(this)
        setContentView(glView)
    }
}
