val directBootContext:Context = appContext.createDeviceProtectedStorageContext()
// For example open a file from there:
val inStream:FileInputStream = directBootContext.openFileInput(filename)
