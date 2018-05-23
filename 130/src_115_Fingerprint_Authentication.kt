val mngr = getSystemService(Context.FINGERPRINT_SERVICE)
as FingerprintManager
val cb = object : FingerprintManager.AuthenticationCallback() {
    override
    fun onAuthenticationSucceeded( result: FingerprintManager.AuthenticationResult) {
        ...}
    override
    fun onAuthenticationFailed() {
        ...}
}
val cs = CancellationSignal()
mngr.authenticate(null, cs, 0, cb, null)
