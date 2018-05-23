class CustomComplicationProviderService : ComplicationProviderService() {
    // This method is for any one-time per complication set-up.
    override
    fun onComplicationActivated( complicationId: Int, dataType: Int, complicationManager: ComplicationManager?) {
        Log.d(TAG, "onComplicationActivated(): $complicationId")
    }
    
    // The complication needs updated data from your
    // provider. Could happen because of one of:
    //   1. An active watch face complication is changed
    //      to use this provider
    //   2. A complication using this provider becomes
    //      active
    //   3. The UPDATE_PERIOD_SECONDS (manifest) has
    //      elapsed
    //   4. Manually: an update via
    //      ProviderUpdateRequester.requestUpdate()
    override fun onComplicationUpdate( complicationId: Int, dataType: Int, complicationManager: ComplicationManager) {
        Log.d(TAG, "onComplicationUpdate() $complicationId")
        
        // ... add code for data generation ...
        
        var complicationData: ComplicationData? = null
        when (dataType) {
            ComplicationData.TYPE_SHORT_TEXT ->
            complicationData = ComplicationData.Builder(ComplicationData.TYPE_SHORT_TEXT)
            . ... create datum ....build()
            ComplicationData.TYPE_LONG_TEXT ->
            complicationData = ComplicationData.Builder(ComplicationData.TYPE_LONG_TEXT)
            ...ComplicationData.TYPE_RANGED_VALUE ->
            complicationData = ComplicationData.Builder(ComplicationData.TYPE_RANGED_VALUE)
            ...else ->
            Log.w("LOG", "Unexpected complication type $dataType")
        }
        
        if (complicationData != null) {
            complicationManager.updateComplicationData( complicationId, complicationData)
        } else {
            // Even if no data is sent, we inform the
            // ComplicationManager
            complicationManager.noUpdateRequired( complicationId)
        }
    }
    
    override
    fun onComplicationDeactivated(complicationId: Int) {
        Log.d("LOG", "onComplicationDeactivated(): $complicationId")
    }
}
