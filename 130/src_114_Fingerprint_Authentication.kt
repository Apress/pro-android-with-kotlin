val useFingerprint = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    (getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager).let {
        it.isHardwareDetected &&
        it.hasEnrolledFingerprints()
    }
} else false
