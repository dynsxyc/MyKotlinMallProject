package com.zhongjiang.net.service

import com.zhongjiang.net.HttpRequestProvider
import com.zhongjiang.net.http.HttpMethod
import com.zhongjiang.net.service.convert.Convert
import com.zhongjiang.net.service.convert.JsonConvert
import java.net.URLEncoder

class IApiProvider {

    companion object{
        private const val ENCODING = "utf-8"
        private val workStation = WorkStation(HttpRequestProvider())
        private val mConvertList = mutableListOf<Convert>(JsonConvert())
        fun  test(url:String,value:MutableMap<String,String>,response:IResponse<Any>){
            var iRequest = IRequest()
//            var wrapperResponse = WrapperResponse(response, mConvertList)
            iRequest.mUrl = url
            iRequest.mMethod = HttpMethod.POST
            iRequest.mData = encodeParam(value)
            iRequest.mResponse = response
            workStation.add(iRequest)
        }
        private fun encodeParam(value:MutableMap<String,String>):ByteArray{
            var buffer = StringBuffer()
            var count = 0
            value.forEach {
                buffer.append(URLEncoder.encode(it.key, ENCODING))
                        .append("=")
                        .append(URLEncoder.encode(it.value, ENCODING))
                if (count != value.size-1){
                    buffer.append("&")
                }
                count++
            }

            return buffer.toString().toByteArray()
        }
    }
}