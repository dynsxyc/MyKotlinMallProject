package com.zhongjiang.net.service

import android.util.Log
import com.ihsanbal.logging.Logger
import com.zhongjiang.net.http.HttpRequest
import com.zhongjiang.net.http.HttpResponse
import java.io.ByteArrayOutputStream

class HttpRunnable(private val mHttpRequest: HttpRequest, private val mRequest:IRequest, private val mWorkStation: WorkStation) : Runnable {
    override fun run() {
        Log.i("testNet" , "runnable  start ")
            mHttpRequest.getBody().write(mRequest.mData)
        mHttpRequest.getHeaders().setContentType("application/json;charset=UTF-8")
        var response = mHttpRequest.execute()

        var contentType = response.getHeaders().getContentType()
        mRequest.mContentType = contentType
        Log.i("testNet" , "response  status = "+response.getStatus())
        Log.i("testNet" , "response = "+response.getBody().toString())
        if (response.getStatus().isSuccess()){
                Log.i("testNet" , "success ${getData(response)}")
                mRequest.mResponse.success(mRequest,getData(response).toString())
        }
        mWorkStation.finish(mRequest)
    }
    private fun getData(httpResponse: HttpResponse):ByteArray{
//        var byteArrayOutputStream = ByteArrayOutputStream(httpResponse.getContentLength())
//        var len:Int
//        var data= Byte(512)
//        while ({len = httpResponse.getBody().read(data)
//                len} !=0){
//            byteArrayOutputStream.write(data,0,len)
//        }
//        return byteArrayOutputStream.toByteArray()
        return httpResponse.getBody().readBytes()



    }
}