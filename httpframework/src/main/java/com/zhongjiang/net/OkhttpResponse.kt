package com.zhongjiang.net

import com.zhongjiang.net.http.HttpHeader
import com.zhongjiang.net.http.HttpStatus
import okhttp3.Response
import java.io.InputStream

class OkhttpResponse(val mResponse: Response) : AbstractHttpResponse() {
    companion object{
        lateinit var mHttpHeader : HttpHeader
    }
    override fun closeInternal() {
        mResponse.body()?.close()
    }

    override fun getBodyInternal(): InputStream? {
        return mResponse.body()?.byteStream()
    }

    override fun getStatus(): HttpStatus {
        return HttpStatus.getValue(mResponse.code())
    }

    override fun getStatusMsg(): String {
       return mResponse.message()
    }

    override fun getHeaders(): HttpHeader {
        if (mHttpHeader === null){
            mHttpHeader = HttpHeader()
            mResponse.headers().names().forEach {
                mResponse.header(it)?.let { it1 -> mHttpHeader.set(it, it1) }
            }
        }
        return mHttpHeader
    }
}