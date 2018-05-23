// An integer you can use to identify that call when the
// called Intent returns
val READ_REQUEST_CODE = 42

// ACTION_OPEN_DOCUMENT used in this example is the
// intent to choose a document like for example a file
// file via the system's file browser.
val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

// Filter to only show results that can be "opened", such
// as a file (as opposed to a list of informational items)
intent.addCategory(Intent.CATEGORY_OPENABLE)

// You can use a filter to for example show only images.
// To search for all documents instead, you can use "*/*"
// here.
intent.type = "image/*"

// The actual Intent call - the system will provide the
// GUI
startActivityForResult(intent, READ_REQUEST_CODE)
