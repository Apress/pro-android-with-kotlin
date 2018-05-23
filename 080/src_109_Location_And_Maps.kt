/**
* Use this to save and manipulate the map once
* available.
*/
override fun onMapReady(map: GoogleMap) {
    // Add a marker in Austin, TX and move the camera
    val austin = LatLng(30.284935, -97.735464)
    map.addMarker(MarkerOptions().position(austin).title("Marker in Austin"))
    map.moveCamera(CameraUpdateFactory.newLatLng(austin))
}
