package com.zhongjiang.net.http

import java.io.OutputStream
import java.net.URI

interface HttpRequest : Header {
    fun getMethod(): HttpMethod
    fun getUri(): URI
    fun getBody():OutputStream
    fun execute(): HttpResponse

}