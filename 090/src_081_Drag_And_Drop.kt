val item = ClipData.Item(myView.tag.toString())
val dragData = ClipData(myView.tag.toString(), arrayOf(MIMETYPE_TEXT_PLAIN), item)
