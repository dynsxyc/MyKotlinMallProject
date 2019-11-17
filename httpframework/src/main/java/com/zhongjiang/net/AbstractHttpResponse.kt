package com.zhongjiang.net

import com.zhongjiang.net.http.HttpResponse
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.zip.GZIPInputStream

abstract class AbstractHttpResponse : HttpResponse {
    private fun isGzip():Boolean{
        val contentEncoding = getHeaders().getContentEncoding()
        if (GZIP == contentEncoding){
            return true
        }
        return false
    }

    override fun getBody(): InputStream {
        val body = getBodyInternal()
        if (isGzip()){
            return getBodyGzip(body)
        }
        return body
    }

    private fun getBodyGzip(inputStream: InputStream):InputStream{
        if (mGzipInputStream === null){
            mGzipInputStream = GZIPInputStream(inputStream)
        }
        return mGzipInputStream!!
    }

    override fun close() {
        mGzipInputStream?.close()
        closeInternal()
    }

    abstract fun closeInternal()

    abstract fun getBodyInternal(): InputStream

    companion object {
        const val GZIP = "gzip"
        private var mGzipInputStream:GZIPInputStream?=null
    }
}