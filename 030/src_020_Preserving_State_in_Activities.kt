override
fun onSaveInstanceState(outState:Bundle?) {
    super.onSaveInstanceState(outState)
    // add your own data to the Bundle here...
    // you can use one of the put* methods here
    // or write your own Parcelable types
}

override
fun onRestoreInstanceState(savedInstanceState: Bundle?) {
    super.onRestoreInstanceState(savedInstanceState)
    // restore your own data from the Bundle here...
    // use one of the get* methods here
}
