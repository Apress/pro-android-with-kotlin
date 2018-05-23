val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
val contentUri = Uri.fromFile(photoFile)
mediaScanIntent.setData(contentUri)
sendBroadcast(mediaScanIntent)
