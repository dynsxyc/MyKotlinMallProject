package com.zhongjiang.youxuan.base.utils

import com.orhanobut.logger.Logger

/**
 * @author dyn
 * @date on 2019/11/6  16:38
 * @packagename com.zhongjiang.youxuan.base.utils
 * @fileName ULogger
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
object ULogger {
    fun i(message: String,vararg anyMessage:Any?){
        Logger.i(message,anyMessage)
    }
    fun i(vararg anyMessage:Any?){
        Logger.i("",anyMessage)
    }
    fun d(message:String = "message",vararg any: Any?){
        Logger.d(message,any)
    }
    fun d(vararg any: Any?){
        Logger.d(any)
    }
    fun e(throwable:Throwable,message: String, any: Any?){
        Logger.e(throwable,message,any)
    }
    fun e(message: String, any: Any?){
        Logger.e(message,any)
    }
    fun w(message: String, any: Any?){
        Logger.w(message,any)
    }
    fun v(message: String, any: Any?){
        Logger.v(message,any)
    }
    fun json(jsonStr: String){
        Logger.json(jsonStr)
    }
    fun xml(xml: String){
        Logger.xml(xml)
    }
}