package com.zhongjiang.net

import com.zhongjiang.net.http.HttpHeader
import com.zhongjiang.net.http.HttpStatus
import okhttp3.Response
import java.io.ByteArrayInputStream
import java.io.InputStream

class OkHttpResponse(private val mResponse: Response) : AbstractHttpResponse() {
    companion object{
        var mHttpHeader = HttpHeader()
    }
    override fun closeInternal() {
        mResponse.body()?.close()
    }

    override fun getBodyInternal(): InputStream {
        var inputStream : InputStream = ByteArrayInputStream(ByteArray(0))
        mResponse.body()?.let {
            inputStream = it.byteStream()
        }

        return inputStream
    }

    override fun getStatus(): HttpStatus {
        return HttpStatus.getValue(mResponse.code())
    }

    override fun getStatusMsg(): String {
       return mResponse.message()
    }

    override fun getHeaders(): HttpHeader {
            mResponse.headers().names().forEach {
                mResponse.header(it)?.let { it1 -> mHttpHeader.set(it, it1) }
            }
        return mHttpHeader
    }

    override fun getContentLength(): Long {
        var result = 0L
        mResponse.body()?.let {
            result = it.contentLength()
        }
        return result
    }
}