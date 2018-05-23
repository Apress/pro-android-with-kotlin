val intent = Intent()
intent.component = ComponentName("de.pspaeth.xyz", "de.pspaeth.xyz.MyReceiver")
intent.action = "de.pspaeth.simplebroadcast.DO_STH"
// add other coords...
sendBroadcast(intent)
