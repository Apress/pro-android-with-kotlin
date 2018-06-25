class AddressResultReceiver(handler: Handler?) : ResultReceiver(handler) {
    override
    fun onReceiveResult(resultCode: Int, resultData: Bundle) {
        val addressOutput = resultData.getString( GeocoderConstants.RESULT_DATA_KEY)
        Log.e("LOG", "address result = " + addressOutput.toString())
        ...}
}
val resultReceiver = AddressResultReceiver(null)
fun startFetchAddress(view:View) {
    if (checkPermission( Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
    {
        fusedLocationClient?.lastLocation?.addOnSuccessListener(this, {
            location: Location? ->
            if (location == null) {
                // TODO
            } else location.apply {
                Log.e("LOG", toString())
                val intent = Intent( this@MainActivity, FetchAddressService::class.java)
                intent.putExtra( GeocoderConstants.RECEIVER, resultReceiver)
                intent.putExtra( GeocoderConstants.LOCATION_DATA_EXTRA, this)
                startService(intent)
            }
        })
    }
}
