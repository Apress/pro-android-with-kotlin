val intent = Intent(this, MyReceiver::class.java)
intent.action = "de.pspaeth.simplebroadcast.DO_STH"
intent.putExtra("myExtra", "myExtraVal")
Log.e("LOG", "Sending broadcast")
LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
Log.e("LOG", "Broadcast sent")
