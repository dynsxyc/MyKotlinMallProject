package com.zhongjiang.net.service

import com.zhongjiang.net.http.HttpMethod

class IRequest {
    lateinit var mUrl:String
    lateinit var mMethod:HttpMethod
    lateinit var mData:ByteArray
    var mContentType:String? = null
    lateinit var mResponse:IResponse<Any>
}