val et = ... // the EditText view
et.addTextChangedListener( object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        // ...
    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // ...
    }
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // ...
    }
})
