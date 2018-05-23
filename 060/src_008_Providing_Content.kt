intent.action = "com.example.app.VIEW" // SET INTENT ACTION
intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
// GRANT TEMPORARY READ PERMISSION
intent.data = Uri.parse("content://<AUTHORITY>/<PATH>")
// USE YOUR OWN!
startActivity(intent) 