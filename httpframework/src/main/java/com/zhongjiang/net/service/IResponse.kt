package com.zhongjiang.net.service

abstract class IResponse<in T> {
    open abstract fun success(request: IRequest, data: T)
    open abstract fun fail(errorCode: Int, errorMessage: String)
}