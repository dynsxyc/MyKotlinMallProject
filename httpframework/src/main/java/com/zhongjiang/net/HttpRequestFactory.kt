package com.zhongjiang.net

import com.zhongjiang.net.http.HttpMethod
import com.zhongjiang.net.http.HttpRequest
import java.net.URI

interface HttpRequestFactory {
    fun createHttpRequest(uti:URI,httpMethod:HttpMethod):HttpRequest
}