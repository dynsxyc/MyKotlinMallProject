package com.zhongjiang.net

import com.zhongjiang.net.http.HttpHeader
import com.zhongjiang.net.http.HttpRequest
import com.zhongjiang.net.http.HttpResponse
import java.io.OutputStream
import java.util.zip.ZipOutputStream

abstract class AbstractHttpRequest : HttpRequest {
    companion object {
        private var mHeader: HttpHeader? = null
        private var mZip: ZipOutputStream? = null
        private var executed: Boolean = false
    }

    override fun getHeaders(): HttpHeader? {
        return mHeader
    }

    override fun getBody(): OutputStream? {
        var body = getBodyOutPutStream()
        if (isGzip()) {
            return getGzipOutputStream(body)
        }
        return body
    }

    private fun getGzipOutputStream(body: OutputStream?): OutputStream? {
        if (mZip === null && body != null) {
            mZip = ZipOutputStream(body)
        }
        return mZip
    }

    private fun isGzip(): Boolean {
        val contentEncoding = getHeaders()?.getContentEncoding()
        if ("gzip" == contentEncoding) {
            return true
        }
        return false
    }

    override fun execute(): HttpResponse {
        mZip?.close()
        var response = executeInternal(mHeader)
        executed = true
        return response
    }

    abstract fun executeInternal(mHeader: HttpHeader?): HttpResponse

    abstract fun getBodyOutPutStream(): OutputStream?
}
