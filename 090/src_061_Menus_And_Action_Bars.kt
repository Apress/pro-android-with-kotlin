override
fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    val inflater = menuInflater
    inflater.inflate(R.menu.context_menu, menu)
}

override
fun onContextItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
        ctxmenu_item1 -> {
            Toast.makeText(this,"CTX Item 1", Toast.LENGTH_LONG).show()
        }
        ctxmenu_item2 -> {
            Toast.makeText(this,"CTX Item 2", Toast.LENGTH_LONG).show()
        }
        else -> return
        super.onContextItemSelected(item)
    }
    return true
}
