package com.zhongjiang.net

import com.zhongjiang.net.http.HttpMethod
import com.zhongjiang.net.http.HttpRequest
import okhttp3.OkHttpClient
import java.net.URI
import java.util.concurrent.TimeUnit

class OkhttpRequestFactory constructor(): HttpRequestFactory {
    constructor(builder: OkHttpClient.Builder) : this()
     {
        this.mBuilder = builder
    }
    private var mBuilder:OkHttpClient.Builder = OkHttpClient.Builder()
    fun setReadTimeOut(readTimeOut:Long){
        mBuilder = mBuilder.readTimeout(readTimeOut,TimeUnit.MILLISECONDS)
    }
    fun setWriteTimeOut(writeTimeOut:Long){
        mBuilder = mBuilder.writeTimeout(writeTimeOut,TimeUnit.MILLISECONDS)
    }
    override fun createHttpRequest(uri: URI, httpMethod: HttpMethod): HttpRequest {
        return OkHttpRequest(mBuilder.build(),httpMethod,uri.toString())
    }
}