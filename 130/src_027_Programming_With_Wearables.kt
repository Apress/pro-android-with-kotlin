fun hasSpeakers(): Boolean {
    val packageManager = context.getPackageManager()
    val audioManager = context.getSystemService( Context.AUDIO_SERVICE) as AudioManager
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        // Check FEATURE_AUDIO_OUTPUT to guard against
        // false positives.
        if (!packageManager.hasSystemFeature( PackageManager.FEATURE_AUDIO_OUTPUT)) {
            return false
        }
        
        val devices = audioManager.getDevices( AudioManager.GET_DEVICES_OUTPUTS)
        for (device in devices) {
            if (device.type == AudioDeviceInfo.TYPE_BUILTIN_SPEAKER) {
                return true
            }
        }
    }
    return false
}
