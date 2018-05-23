class MapsActivity : WearableActivity(), OnMapReadyCallback {
    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        setAmbientEnabled() // Enables always on
        setContentView(R.layout.activity_maps)
        
        // Enables the Swipe-To-Dismiss Gesture
        ...// Adjusts margins
        ...}
    override
    fun onMapReady(googleMap: GoogleMap) {
        ...}
}
