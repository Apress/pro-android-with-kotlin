val locationUpdates = object : LocationCallback() {
    override fun onLocationResult(lr: LocationResult) {
        Log.e("LOG", lr.toString())
        Log.e("LOG", "Newest Location: " + lr.locations.last())
        // do something with the new location...
    }
}
fusedLocationClient?.requestLocationUpdates(reqSetting, locationUpdates, null /* Looper */)
