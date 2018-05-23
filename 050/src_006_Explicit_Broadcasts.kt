val intent = Intent(this, MyReceiver::class.java)
intent.action = "de.pspaeth.myapp.DO_STH"
intent.putExtra("myExtra", "myExtraVal")
sendBroadcast(intent)
