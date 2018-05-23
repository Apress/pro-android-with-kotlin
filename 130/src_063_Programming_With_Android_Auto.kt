public void onCreate() {
    super.onCreate()
    ...// Start a MediaSession
    val mSession = MediaSessionCompat( this, "my session tag")
    val token:MediaSessionCompat.Token = mSession.sessionToken
    
    // Set a callback object to handle play
    /control requests
    mSession.setCallback( object : MediaSessionCompat.Callback() {
        // overwrite methods here for
        // playback controls...
    })
    ...}
