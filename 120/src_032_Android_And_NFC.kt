override
fun onResume() {
    super.onResume()
    // Check to see that the Activity started due to an
    // Android Beam event
    if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
        processIntent(intent)
    }
}
