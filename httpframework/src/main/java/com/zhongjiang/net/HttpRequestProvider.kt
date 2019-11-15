package com.zhongjiang.net

import com.zhongjiang.net.http.HttpMethod
import com.zhongjiang.net.http.HttpRequest
import com.zhongjiang.net.origin.OriginHttpRequestFactory
import com.zhongjiang.net.utils.Utils
import okhttp3.OkHttpClient
import java.net.URI

class HttpRequestProvider {
    companion object{
        private lateinit var mHttpRequestFactory:HttpRequestFactory
        private var OKHTTP_REQUEST:Boolean = Utils.isExist("okhttp3.OkHttpClient",this::class.java.classLoader);
    }
    init {
        if (OKHTTP_REQUEST){
            mHttpRequestFactory = OkHttpRequestFactory(OkHttpClient.Builder())
        }else{
            mHttpRequestFactory = OriginHttpRequestFactory()
        }

    }
    fun getHttpRequest(uri: URI,httpMethod: HttpMethod):HttpRequest{
        return mHttpRequestFactory.createHttpRequest(uri,httpMethod)
    }


}