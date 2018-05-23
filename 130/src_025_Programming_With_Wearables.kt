val SPEECH_REQUEST_CODE = 42
val intent = Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
}.run {
    startActivityForResult(this, SPEECH_REQUEST_CODE)
}
