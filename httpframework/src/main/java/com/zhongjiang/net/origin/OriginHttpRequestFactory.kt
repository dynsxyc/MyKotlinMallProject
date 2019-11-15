package com.zhongjiang.net.origin

import com.zhongjiang.net.HttpRequestFactory
import com.zhongjiang.net.http.HttpMethod
import com.zhongjiang.net.http.HttpRequest
import java.net.HttpURLConnection
import java.net.URI

class OriginHttpRequestFactory() : HttpRequestFactory {
    companion object{
        lateinit var mHttpUrlConnection : HttpURLConnection
    }
    override fun setReadTimeOut(timeOut: Long) {
        mHttpUrlConnection.readTimeout = timeOut.toInt()
    }

    override fun setWriteTimeOut(timeOut: Long) {

    }

    override fun setConnectionTimeOut(timeOut: Long) {
        mHttpUrlConnection.connectTimeout = timeOut.toInt()
    }

    override fun createHttpRequest(uri: URI, httpMethod: HttpMethod): HttpRequest {
        return OriginHttpRequest(uri.toURL().openConnection() as HttpURLConnection,httpMethod,uri.toString())
    }
}