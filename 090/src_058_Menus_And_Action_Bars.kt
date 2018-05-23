override
fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.getItemId()) {
        menu_item1 -> {
            Toast.makeText(this,"Item 1", Toast.LENGTH_LONG).show()
            return true
        }
        menu_item2 -> {
            Toast.makeText(this,"Item 2", Toast.LENGTH_LONG).show()
            return true
        }
        else -> return
        super.onOptionsItemSelected(item)
    }
}

override
fun onCreateOptionsMenu(menu: Menu): Boolean {
    val inflater = menuInflater
    inflater.inflate(R.menu.my_menu, menu)
    return true
}
