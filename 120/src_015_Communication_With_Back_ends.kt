class RequestQueueSingleton
constructor (context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: RequestQueueSingleton? = null
        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: RequestQueueSingleton(context)
        }
    }
    val requestQueue: RequestQueue by lazy {
        val alwaysTrusting = object : HurlStack() {
            override
            fun createConnection(url: URL): HttpURLConnection {
                fun getHostnameVerifier():HostnameVerifier {
                    return object : HostnameVerifier {
                        override
                        fun verify(hostname:String, session:SSLSession):Boolean = true
                    }
                }
                return (super.createConnection(url) as HttpsURLConnection).apply {
                    hostnameVerifier = getHostnameVerifier()
                }
            }
        }
        // Using the Application context is important.
        // This is for testing:
        Volley.newRequestQueue(context.applicationContext, alwaysTrusting)
        // ... for production use:
        // Volley.newRequestQueue(context.applicationContext)
    }
}
