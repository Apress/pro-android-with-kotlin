class Log {
    companion object {
        val ENABLED = true
        
        fun v(tag: String, msg: String) {
            if(!ENABLED) return
            // <- add this to all the other statements
            android.util.Log.v(tag, msg)
        }
        ...}
}
