class FetchAddressService : IntentService("FetchAddressService") {
    
    override
    fun onHandleIntent(intent: Intent?) {
        val geocoder = Geocoder(this, Locale.getDefault())
        var errorMessage = ""
        
        // Get the location passed to this service through
        // an extra.
        val location = intent?.getParcelableExtra( GeocoderConstants.LOCATION_DATA_EXTRA)
        as Location
        
        // Get the Intent result receiver
        val receiver = intent.getParcelableExtra( GeocoderConstants.RECEIVER) as ResultReceiver
        
        var addresses: List<Address>? = null
        try {
            addresses = geocoder.getFromLocation( location.getLatitude(), location.getLongitude(), 1) // Get just a single address!
        } catch (e: IOException) {
            // Catch network or other I/O problems.
            errorMessage = "service_not_available"
            Log.e("LOG", errorMessage, e)
        } catch (e: IllegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "invalid_lat_long_used"
            Log.e("LOG", errorMessage + ". " + "Latitude = " + location.getLatitude() + ", Longitude = " + location.getLongitude(), e)
        }
        
        if (addresses == null || addresses.size == 0) {
            // No address was found.
            if (errorMessage.isEmpty()) {
                errorMessage = "no_address_found"
                Log.e("LOG", errorMessage)
            }
            deliverResultToReceiver( receiver, GeocoderConstants.FAILURE_RESULT, errorMessage)
        } else {
            val address = addresses[0]
            val addressFragments = (0..address.maxAddressLineIndex).map { i -> address.getAddressLine(i) }
            val addressStr = addressFragments.joinToString( separator = System.getProperty("line.separator"))
            Log.i("LOG", "address_found")
            deliverResultToReceiver( receiver, GeocoderConstants.SUCCESS_RESULT, addressStr)
        }
    }
    
    private fun deliverResultToReceiver( receiver:ResultReceiver, resultCode: Int, message: String) {
        val bundle = Bundle()
        bundle.putString(GeocoderConstants.RESULT_DATA_KEY, message)
        receiver.send(resultCode, bundle)
    }
}
