package com.zhongjiang.net

import com.zhongjiang.net.http.HttpHeader
import com.zhongjiang.net.http.HttpRequest
import com.zhongjiang.net.http.HttpResponse
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.util.zip.ZipException
import java.util.zip.ZipOutputStream

abstract class AbstractHttpRequest : HttpRequest {
    companion object {
        private var mHeader = HttpHeader()
        private val emptyByte = ByteArrayOutputStream()
        private lateinit var mZip :ZipOutputStream
        private var executed: Boolean = false
    }
    init {
        emptyByte.write(byteArrayOf())
       mZip = ZipOutputStream(emptyByte)
    }

    override fun getHeaders(): HttpHeader {
        return mHeader
    }

    override fun getBody(): OutputStream {
        var body = getBodyOutPutStream()
        if (isGzip()) {
            return getGzipOutputStream(body)
        }
        return body
    }

    private fun getGzipOutputStream(body: OutputStream): OutputStream {
            mZip = ZipOutputStream(body)
        return mZip
    }

    private fun isGzip(): Boolean {
        val contentEncoding = getHeaders().getContentEncoding()
        if ("gzip" == contentEncoding) {
            return true
        }
        return false
    }

    override fun execute(): HttpResponse {
        try {
            mZip.close()
        }catch (e:ZipException){
            e.printStackTrace()
        }
        var response = executeInternal(mHeader)
        executed = true
        return response
    }

    abstract fun executeInternal(mHeader: HttpHeader): HttpResponse

    abstract fun getBodyOutPutStream(): OutputStream
}
