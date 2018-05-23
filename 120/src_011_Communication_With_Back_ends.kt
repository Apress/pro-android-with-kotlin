val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
    override
    fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate>? = null
    override
    fun checkClientTrusted( certs: Array<java.security.cert.X509Certificate>, authType: String) {
    }
    override
    fun checkServerTrusted( certs: Array<java.security.cert.X509Certificate>, authType: String) {
    }
})
SSLContext.getInstance("SSL").apply {
    init(null, trustAllCerts, java.security.SecureRandom())
}.apply {
    HttpsURLConnection.setDefaultSSLSocketFactory(
    socketFactory)
}
