class Log {
    companion object {
        fun v(tag: String, msg: String) {
            randroid.util.Log.v(tag, msg)
        }
        
        fun v(tag: String, msg: String, tr: Throwable) {
            android.util.Log.v(tag, msg, tr)
        }
        
        fun d(tag: String, msg: String) {
            android.util.Log.d(tag, msg)
        }
        
        fun d(tag: String, msg: String, tr: Throwable) {
            android.util.Log.d(tag, msg, tr)
        }
        
        fun i(tag: String, msg: String) {
            android.util.Log.i(tag, msg)
        }
        
        fun i(tag: String, msg: String, tr: Throwable) {
            android.util.Log.i(tag, msg, tr)
        }
        
        fun w(tag: String, msg: String) {
            android.util.Log.w(tag, msg)
        }
        
        fun w(tag: String, msg: String, tr: Throwable) {
            android.util.Log.w(tag, msg, tr)
        }
        
        fun w(tag: String, tr: Throwable) {
            android.util.Log.w(tag, tr)
        }
        
        fun e(tag: String, msg: String) {
            android.util.Log.e(tag, msg)
        }
        
        fun e(tag: String, msg: String, tr: Throwable) {
            android.util.Log.e(tag, msg, tr)
        }
    }
}
