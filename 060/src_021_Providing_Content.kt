val uri = ... // a content uri
contentResolver.registerContentObserver(uri, true, object : ContentObserver(null) {
    override fun onChange(selfChange: Boolean) {
        // do s.th.
    }
    override fun onChange(selfChange: Boolean, uri: Uri?) {
        // do s.th.
    }
}
)
