package com.zhongjiang.net.http

import java.io.Closeable
import java.io.InputStream

interface HttpResponse : Header,Closeable {
    fun getStatus(): HttpStatus
    fun getStatusMsg():String
    fun getBody():InputStream?
}