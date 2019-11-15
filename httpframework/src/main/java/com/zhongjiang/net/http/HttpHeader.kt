package com.zhongjiang.net.http

class HttpHeader : NameValueMap<String, String> {
    companion object {
        const val ACCEPT = "Accept"
        const val ACCEPT_ENCODING = "Accept-Encoding"
        const val PRAGMA = "Pragma"
        const val PROXY_CONNECTION = "Proxy-Connection"
        const val USER_AGENT = "User-Agent"
        const val CACHE_CONTROL = "Cache-Control"
        const val CONTENT_ENCODING = "Content-Encoding"
        const val CONNECTION = "Connection"
        const val CONTENT_LENGTH = "Content-Length"
    }

    fun getAccept(): String? {
        return get(ACCEPT)
    }

    fun setAccept(accept: String) {
        put(ACCEPT, accept)
    }

    fun getPragma(): String? {
        return get(PRAGMA)
    }

    fun setPragma(pragma: String) {
        put(PRAGMA, pragma)
    }

    fun getUserAgent(): String? {
        return get(USER_AGENT)
    }

    fun setUserAgent(userAgent: String) {
        put(USER_AGENT, userAgent)
    }

    fun getProxyConnection(): String? {
        return get(PROXY_CONNECTION)
    }

    fun setProxyConnection(proxyConnection: String) {
        put(PROXY_CONNECTION, proxyConnection)
    }

    fun getAcceptEncoding(): String? {
        return get(ACCEPT_ENCODING)
    }

    fun setAcceptEncoding(acceptEncoding: String) {
        put(ACCEPT_ENCODING, acceptEncoding)
    }
    fun getCacheControl(): String? {
        return get(CACHE_CONTROL)
    }

    fun setCacheControl(cacheControl: String) {
        put(CACHE_CONTROL, cacheControl)
    }
    fun getContentEncoding(): String? {
        return get(CONTENT_ENCODING)
    }

    fun setContentEncoding(contentEncoding: String) {
        put(CONTENT_ENCODING, contentEncoding)
    }
    fun getConnection(): String? {
        return get(CONNECTION)
    }

    fun setConnection(connection: String) {
        put(CONNECTION, connection)
    }
    fun getContentLength(): String? {
        return get(CONTENT_LENGTH)
    }

    fun setContentLength(contentLength: String) {
        put(CONTENT_LENGTH, contentLength)
    }


    private var mMap = mutableMapOf<String, String>()

    override fun get(key: String): String? {
        return mMap[key]
    }

    override fun set(key: String, value: String) {
        mMap[key] = value
    }

    override fun setAll(mutableMap: MutableMap<String, String>) {
        mMap.putAll(mutableMap)
    }

    override val size: Int
        get() = mMap.size

    override fun containsKey(key: String): Boolean {
        return mMap.containsKey(key)
    }

    override fun containsValue(value: String): Boolean {
        return mMap.containsValue(value)
    }

    override fun isEmpty(): Boolean {
        return mMap.isEmpty()
    }

    override val entries: MutableSet<MutableMap.MutableEntry<String, String>>
        get() = mMap.entries
    override val keys: MutableSet<String>
        get() = mMap.keys
    override val values: MutableCollection<String>
        get() = mMap.values

    override fun clear() {
        mMap.clear()
    }

    override fun put(key: String, value: String): String? {
        return mMap.put(key, value)
    }

    override fun putAll(from: Map<out String, String>) {
        mMap.putAll(from)
    }

    override fun remove(key: String): String? {
        return mMap.remove(key)
    }
}