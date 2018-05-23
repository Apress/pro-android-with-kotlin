if (checkPermission( Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)) {
    fusedLocationClient?.lastLocation?.addOnSuccessListener(this, {location : Location? ->
        // Got last known location. In some rare
        // situations this can be null.
        if(location == null) {
            // TODO, handle it
        } else location.apply {
            // Handle location object
            Log.e("LOG", location.toString())
        }
    })
}
