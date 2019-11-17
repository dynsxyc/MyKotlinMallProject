package com.zhongjiang.net.service

import android.util.Log
import com.zhongjiang.net.service.convert.Convert
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class WrapperResponse(private val mResponse:IResponse<Any>, private val mConverts: MutableList<Convert>) : IResponse<String>() {
    override fun success(request: IRequest, data: String) {
        mConverts.forEach {
            Log.i("testNet" , "${request.mResponse}---${request.mContentType}----${it.isConvert(request.mContentType)}")
            if (it.isConvert(request.mContentType)){
                var resultData = it.pares(data,getType())
                request.mResponse.success(request,resultData)
                return@forEach
            }
        }
    }

    private fun getType():Type{
        var type = mResponse.javaClass.genericSuperclass
        var paramType = arrayOf<Type>()
        type?.let {
            if (type is ParameterizedType){
                 paramType=type.actualTypeArguments
            }
        }
        return paramType.first()
    }

    override fun fail(errorCode: Int, errorMessage: String) {

    }
}