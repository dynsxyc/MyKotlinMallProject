package com.zhongjiang.net.origin

import com.zhongjiang.net.BufferHttpRequest
import com.zhongjiang.net.http.HttpHeader
import com.zhongjiang.net.http.HttpMethod
import com.zhongjiang.net.http.HttpResponse
import java.net.HttpURLConnection
import java.net.URI

class OriginHttpRequest(val mHttpURLConnection: HttpURLConnection,val mMethod: HttpMethod,val mUrl:String) : BufferHttpRequest() {

    override fun executeInternal(httpHeader: HttpHeader?, data: ByteArray?): HttpResponse {
        httpHeader?.forEach{
            mHttpURLConnection.addRequestProperty(it.key,it.value)
        }
        mHttpURLConnection.doInput = true
        mHttpURLConnection.doOutput = true
        mHttpURLConnection.requestMethod = mMethod.name
        data?.let {
            if (data.isNotEmpty()){
                mHttpURLConnection.outputStream.write(data,0,data.size)
                mHttpURLConnection.outputStream.close()
            }
        }
        mHttpURLConnection.connect()
        return OriginHttpResponse(mHttpURLConnection)
    }

    override fun getMethod(): HttpMethod {
        return mMethod
    }

    override fun getUri(): URI {
        return URI.create(mUrl)
    }
}