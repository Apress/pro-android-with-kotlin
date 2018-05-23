companion object {
    ...private val mLogConfigrator = LogConfigurator().apply {
        fileName = Environment.getExternalStorageDirectory().toString() + "/" + "log4j.log"
        maxFileSize = (1024 * 1024).toLong()
        filePattern = "%d - [%c] - %p : %m%n"
        maxBackupSize = 10
        isUseLogCatAppender = true
        configure()
    }
    
    private var ENABLED = true // or, see above
    // private var ENABLED = BuildConfig.LOG
    
    fun v(tag: String, msg: String) {
        if(!ENABLED) return
        Logger.getLogger(tag).trace(msg)
        // <- add similar lines to all the other
        // statements
    }
    ...}
