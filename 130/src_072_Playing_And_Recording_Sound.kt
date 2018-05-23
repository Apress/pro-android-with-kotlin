class SoundLoadManager(val ctx:Context) {
    var scheduled = 0
    var loaded = 0
    val sndPool:SoundPool
    val soundPoolMap = mutableMapOf<Int,Int>()
    init {
        sndPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes( AudioAttributes.Builder() .setUsage( AudioAttributes.USAGE_MEDIA) .setContentType( AudioAttributes.CONTENT_TYPE_MUSIC) .build() ).build()
        } else {
            SoundPool(4, AudioManager.STREAM_MUSIC, 100)
        }
        sndPool.setOnLoadCompleteListener({
            sndPool, sampleId, status ->
            if(status != 0) {
                Log.e("LOG", "Sound could not be loaded")
            } else {
                Log.i("LOG", "Loaded sample " + sampleId + ", status = " + status)
            }
            loaded++ })
    }
    
    fun load(resourceId:Int) {
        scheduled++ soundPoolMap[resourceId] = sndPool.load(ctx, resourceId, 1)
    }
    
    fun allLoaded() = scheduled == loaded
    
    fun play(rsrcId: Int, loop: Boolean):Int {
        return soundPoolMap[rsrcId]?.run {
            val audioManager = ctx.getSystemService(
            Context.AUDIO_SERVICE) as AudioManager
            val curVolume = audioManager.getStreamVolume(
            AudioManager.STREAM_MUSIC)
            val maxVolume = audioManager.getStreamMaxVolume(
            AudioManager.STREAM_MUSIC)
            val leftVolume = 1f * curVolume / maxVolume
            val rightVolume = 1f * curVolume / maxVolume
            val priority = 1
            val noLoop = if(loop) -1 else 0
            val normalPlaybackRate = 1f
            sndPool.play(this, leftVolume, rightVolume, priority, noLoop, normalPlaybackRate)
        } ?: -1
    }
}
