package com.zhongjiang.net.service.convert

import com.zhongjiang.net.http.HttpResponse
import java.lang.reflect.Type

interface Convert {
    fun pares(httpResponse:HttpResponse,type:Type):Any
    fun pares(value:String,type:Type):Any
    fun isConvert(contentType:String?):Boolean
}