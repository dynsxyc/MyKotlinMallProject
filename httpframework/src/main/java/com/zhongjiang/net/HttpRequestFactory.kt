package com.zhongjiang.net

import com.zhongjiang.net.http.HttpMethod
import com.zhongjiang.net.http.HttpRequest
import java.net.URI

interface HttpRequestFactory {
    fun createHttpRequest(uri:URI,httpMethod:HttpMethod):HttpRequest
    fun setReadTimeOut(timeOut:Long)
    fun setWriteTimeOut(timeOut:Long)
    fun setConnectionTimeOut(timeOut:Long)
}