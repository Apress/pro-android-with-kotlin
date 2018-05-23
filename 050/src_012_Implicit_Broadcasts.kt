val intent = Intent()
intent.action = "de.pspaeth.myapp.DO_STH"
intent.addCategory("de.pspaeth.myapp.CATEG1")
intent.addCategory("de.pspaeth.myapp.CATEG2")
// ... more categories
intent.type = "text/html"
intent.data = Uri.parse("content://myContent")
intent.putExtra("EXTRA_KEY", "extraVal") intent.flags = ...