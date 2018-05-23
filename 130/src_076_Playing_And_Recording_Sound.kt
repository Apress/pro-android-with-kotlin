var mPlayer: MediaPlayer? = null
fun btnText(playing:Boolean) {
    startBtn.text = if(playing) "Pause" else "Play"
}
fun goStart(v:View) {
    mPlayer = mPlayer?.run {
        btnText(!isPlaying)
        if(isPlaying)
        pause()
        else
        start()
        this
    } ?: MediaPlayer().apply {
        setOnCompletionListener {
            btnText(false)
            release()
            mPlayer = null
        }
        val fd: AssetFileDescriptor = assets.openFd("tune1.mp3")
        setDataSource(fd.fileDescriptor)
        prepare() // synchronous
        start()
        btnText(true)
    }
}

fun goStop(v:View) {
    mPlayer?.run {
        stop()
        prepare()
        btnText(false)
    }
}
