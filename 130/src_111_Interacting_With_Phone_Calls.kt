val num = "+34111222333"
val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", num, null))
startActivity(intent)
