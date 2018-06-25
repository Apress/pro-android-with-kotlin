val REQUEST_CHECK_STATE = 12300 // any suitable ID
val builder = LocationSettingsRequest.Builder()
.addLocationRequest(reqSetting)
val client = LocationServices.getSettingsClient(this)
client.checkLocationSettings(builder.build()).addOnCompleteListener { task ->
    try {
        val state: LocationSettingsStates = task.result.locationSettingsStates
        Log.e("LOG", "LocationSettings: \n" + "  BLE present: ${state.isBlePresent} \n" + "  BLE usable: ${state.isBleUsable} \n" + "  GPS present:  ${state.isGpsPresent} \n" + "  GPS usable: ${state.isGpsUsable} \n" + "  Location present: " + "${state.isLocationPresent} \n" + "  Location usable: " + "${state.isLocationUsable} \n" + "  Network Location present: " + "${state.isNetworkLocationPresent} \n" + "  Network Location usable: " + "${state.isNetworkLocationUsable} \n" )
    } catch (e: RuntimeExecutionException) {
        if (e.cause is ResolvableApiException)
        (e.cause as ResolvableApiException).startResolutionForResult( this@MainActivity, REQUEST_CHECK_STATE)
    }
}
