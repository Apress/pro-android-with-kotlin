fun stopPeriodic(view:View) {
    fusedLocationClient?.removeLocationUpdates(locationUpdates)
}
