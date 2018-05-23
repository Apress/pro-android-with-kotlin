val emailIntent:Intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","abc@gmail.com", null))
emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
startActivity(Intent.createChooser( emailIntent, "Send email..."))
// or startActivity(emailIntent) if you want to use
// the standard chooser (or none, if there is only
// one possible receiver).
