fun convertStreamToString(istr: InputStream): String {
    val s = Scanner(istr).useDelimiter("\\A")
    return if (s.hasNext()) s.next() else ""
}

// This is a convention for emulated devices
// addressing the host (development PC)
val HOST_IP = "10.0.2.2"

val url = "https://${HOST_IP}:6699/test/person"
var stream: InputStream? = null
var connection: HttpsURLConnection? = null
var result: String? = null
try {
    connection = (URL(uri.toString()).openConnection() as HttpsURLConnection).apply {
        
        // ! ONLY FOR TESTING !  No SSL hostname verification
        class TrustAllHostNameVerifier : HostnameVerifier {
            override
            fun verify(hostname: String, session: SSLSession): Boolean = true
        }
        hostnameVerifier = TrustAllHostNameVerifier()
        
        // Timeout for reading InputStream set to 3000ms
        readTimeout = 3000
        // Timeout for connect() set to 3000ms.
        connectTimeout = 3000
        // For this use case, set HTTP method to GET.
        requestMethod = "GET"
        // Already true by default, just telling. Needs to
        // be true since this request is carrying an input
        // (response) body.
        doInput = true
        // Open communication link
        connect()
        responseCode.takeIf {
            it != HttpsURLConnection.HTTP_OK }?.run {
            throw IOException("HTTP error code: $this")
        }
        // Retrieve the response body
        stream = inputStream?.also {
            result = it.let { convertStreamToString(it) }
        }
    }
} finally {
    stream?.close()
    connection?.disconnect()
}

Log.e("LOG", result)
