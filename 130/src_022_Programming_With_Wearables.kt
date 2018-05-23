class MainActivity : FragmentActivity(), AmbientModeSupport.AmbientCallbackProvider {
    override
    fun getAmbientCallback(): AmbientModeSupport.AmbientCallback
    {
        ...}
    
    lateinit
    var mAmbientController: AmbientModeSupport.AmbientController
    
    override
    fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        ...mAmbientController = AmbientModeSupport.attach(this)
    }
}
