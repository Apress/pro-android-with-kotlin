fun hasGps():Boolean {
    return packageManager.hasSystemFeature( PackageManager.FEATURE_LOCATION_GPS);
}
