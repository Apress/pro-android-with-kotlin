val msg = Message.obtain()
val bundle = Bundle()
bundle.putString("MyString", "A message to be sent")
msg.data = bundle
remoteSrvc?.send(msg)
