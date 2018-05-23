setSupportActionBar(toolbar)
supportActionBar!!.setDisplayHomeAsUpEnabled(true)
// The navigation button from the toolbar does not
// do the same as the BACK button, more precisely
// it does not call the onBackPressed() method.
// We add a listener to do it ourselves
toolbar.setNavigationOnClickListener { onBackPressed() }
