package com.zhongjiang.net

import com.zhongjiang.net.http.HttpHeader
import com.zhongjiang.net.http.HttpResponse
import java.io.ByteArrayOutputStream
import java.io.OutputStream

abstract class BufferHttpRequest : AbstractHttpRequest() {
    companion object {
        var mByteArray: ByteArrayOutputStream? = null
    }

    override fun getBodyOutPutStream(): OutputStream? {
        return mByteArray
    }

    override fun executeInternal(header: HttpHeader?): HttpResponse {
        var data: ByteArray? = null
        mByteArray?.let { data = it.toByteArray() }
        return executeInternal(header, data)
    }

    abstract fun executeInternal(httpHeader: HttpHeader?, data: ByteArray?): HttpResponse
}