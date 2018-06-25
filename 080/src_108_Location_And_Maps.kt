override fun onCreate(savedInstanceState: Bundle?) {
    ...val mapFragment = supportFragmentManager
    .findFragmentById(R.id.map)
    as SupportMapFragment
    mapFragment.getMapAsync(this)
}
