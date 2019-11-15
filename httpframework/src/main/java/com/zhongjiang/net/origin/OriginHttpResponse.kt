package com.zhongjiang.net.origin

import com.zhongjiang.net.AbstractHttpResponse
import com.zhongjiang.net.http.HttpHeader
import com.zhongjiang.net.http.HttpStatus
import java.io.InputStream
import java.net.HttpURLConnection

class OriginHttpResponse(val mURLConnection:HttpURLConnection) : AbstractHttpResponse() {
    override fun closeInternal() {
            mURLConnection.disconnect()
    }

    override fun getBodyInternal(): InputStream? {
        return mURLConnection.inputStream
    }

    override fun getStatus(): HttpStatus {
        return HttpStatus.getValue(mURLConnection.responseCode)
    }

    override fun getStatusMsg(): String {
        return mURLConnection.responseMessage
    }

    override fun getHeaders(): HttpHeader {
        var  httpHeader = HttpHeader()
        mURLConnection.headerFields?.let {
            it?.forEach{ item->
                item.key?.let {
                    httpHeader.set(item.key,item.value[0])
                }
            }
        }
        return httpHeader
    }
}