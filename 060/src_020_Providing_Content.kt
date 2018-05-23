val fd = ... // get the AssetFileDescriptor
val inpStream = AssetFileDescriptor.AutoCloseInputStream(fd)
val outpStream = AssetFileDescriptor.AutoCloseOutputStream(fd)
