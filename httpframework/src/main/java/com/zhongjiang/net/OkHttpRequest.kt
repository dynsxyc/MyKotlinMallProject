package com.zhongjiang.net

import com.zhongjiang.net.http.HttpHeader
import com.zhongjiang.net.http.HttpMethod
import com.zhongjiang.net.http.HttpResponse
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.net.URI

class OkHttpRequest(val mClient:OkHttpClient, val mMethod: HttpMethod, val mUrl:String):BufferHttpRequest() {
    override fun executeInternal(httpHeader: HttpHeader, data: ByteArray): HttpResponse {
        val isBody = mMethod == HttpMethod.POST
        var requestBody:RequestBody? = null
        if (isBody){
            requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),data)
        }
        val builder = Request.Builder().url(mUrl).method(mMethod.name,requestBody)
        httpHeader.forEach{
            builder.addHeader(it.key,it.value)
        }
        var response = mClient.newCall(builder.build()).execute()
        return OkhttpResponse(response)
    }

    override fun getMethod(): HttpMethod {
        return mMethod
    }

    override fun getUri(): URI {
        return URI.create(mUrl)
    }
}