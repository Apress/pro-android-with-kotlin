val text = "A NFC message at " + System.currentTimeMillis().toString()
val msg = NdefMessage( arrayOf( NdefRecord.createMime( "application/vnd.com.example.android.beam", text.toByteArray() ) ) )

/*
* When a device receives an NFC message with an Android
* Application Record (AAR) added, the application
* specified in the AAR is guaranteed to run. The AAR
* thus overrides the tag dispatch system.
*/
//val msg = NdefMessage( arrayOf(
//      NdefRecord.createMime(
//           "application/vnd.com.example.android.beam",
//           text.toByteArray() ),
//      NdefRecord.createApplicationRecord(
//           "com.example.android.beam")
//) )
return msg
