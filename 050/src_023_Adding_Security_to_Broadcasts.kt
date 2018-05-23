val intent = Intent()
intent.action = "de.pspaeth.myapp.DO_STH"
// ... more intent coordinates
sendBroadcast(intent, "com.xyz.theapp.PERMISSION1")
