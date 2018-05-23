val reqSetting = LocationRequest.create().apply {
    fastestInterval = 10000
    interval = 10000
    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    smallestDisplacement = 1.0f
}
