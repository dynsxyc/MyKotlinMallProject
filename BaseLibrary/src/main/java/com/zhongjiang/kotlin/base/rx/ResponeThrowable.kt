package com.zhongjiang.kotlin.base.rx

/**
 * Created by dyn on 2018/7/16.
 */
class ResponeThrowable (throwable: Throwable?, val status:Int, val msg:String ) :Throwable(throwable){
    constructor(throwable: Throwable?, status: Int,professional_status:Int, msg: String) : this(throwable,status,msg)
}