val CACHE_CAPACITY = 1024 * 1024 // 1MB
val cache = DiskBasedCache(cacheDir, CACHE_CAPACITY)
// ... or a different implementation
val network = BasicNetwork(HurlStack())
// ... or a different implementation

val requestQueue = RequestQueue(cache, network).apply {
    start()
}
