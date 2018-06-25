class MainActivity : AppCompatActivity() {
    companion object {
        val MY_DATA_CHECK_CODE = 42
    }
    
    var tts: TextToSpeech? = null
    
    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val checkIntent = Intent()
        checkIntent.action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE)
    }
    
    fun go(view: View) {
        tts?.run {
            language = Locale.US
            val myText1 = "Did you sleep well?"
            val myText2 = "It's time to wake up."
            speak(myText1, TextToSpeech.QUEUE_FLUSH, null)
            speak(myText2, TextToSpeech.QUEUE_ADD, null)
        }
    }
    
    override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                tts = TextToSpeech(this, { status ->
                    // do s.th. if you like
                })
            } else {
                // data are missing, install it
                val installIntent = Intent()
                installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
                startActivity(installIntent)
            }
        }
    }
}
