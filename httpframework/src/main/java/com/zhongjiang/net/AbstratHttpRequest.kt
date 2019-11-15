package com.zhongjiang.net

import com.zhongjiang.net.http.HttpHeader
import com.zhongjiang.net.http.HttpRequest
import com.zhongjiang.net.http.HttpResponse
import java.io.OutputStream
import java.util.zip.ZipOutputStream

abstract class AbstratHttpRequest: HttpRequest {
    companion object{
        private lateinit var mHeader: HttpHeader
        private lateinit var mZip:ZipOutputStream
        private var executed:Boolean = false
    }
    override fun getHeaders(): HttpHeader {
        return mHeader
    }

    override fun getBody(): OutputStream {
        var body = getBodyOutPutStream()
        if (isGzip()){
            return getGzipOutputStream(body)
        }
        return body
    }

    private fun getGzipOutputStream(body: OutputStream): OutputStream {
        if (mZip === null){
            mZip = ZipOutputStream(body)
        }
        return mZip
    }

    private fun isGzip():Boolean{
        val contentEncoding = getHeaders().getContentEncoding()
        if ("gzip" == contentEncoding){
            return true
        }
        return false
    }

    override fun execute(): HttpResponse {
        if (mZip != null){
            mZip.close()
        }
        var response = executeInternal(mHeader)
        executed = true
        return response
    }

    abstract fun executeInternal(mHeader: HttpHeader): HttpResponse

    abstract fun getBodyOutPutStream(): OutputStream
}
