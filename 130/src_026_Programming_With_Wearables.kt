fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent) {
    if (requestCode and 0xFFFF == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
        val results = data.getStringArrayListExtra( RecognizerIntent.EXTRA_RESULTS)
        String spokenText = results[0]
        // ... do something with spoken text
    }
    super.onActivityResult( requestCode, resultCode, data)
}
