val fd = ... // get the ParcelFileDescriptor
val inpStream = ParcelFileDescriptor.AutoCloseInputStream(fd)
val outpStream = ParcelFileDescriptor.AutoCloseOutputStream(fd)
