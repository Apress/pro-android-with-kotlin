val wifiLock = (applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
.createWifiLock(WifiManager.WIFI_MODE_FULL, "myWifilock")
.run {
    acquire()
    this
}
... later: wifiLock.release()
