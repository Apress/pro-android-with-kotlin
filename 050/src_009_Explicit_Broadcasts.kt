val intent = Intent(this, MyReceiver::class.java)
intent.action = "de.pspaeth.myapp.DO_STH"
// add other coords...
sendBroadcast(intent)
