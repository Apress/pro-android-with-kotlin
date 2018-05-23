fun showPopup(v: View) {
    PopupMenu(this, v).run {
        setOnMenuItemClickListener { menuItem ->
            Toast.makeText(this@TheActivity, menuItem.toString(), Toast.LENGTH_LONG).show()
            true
        }
        menuInflater.inflate(popup, menu)
        show()
    }
}
