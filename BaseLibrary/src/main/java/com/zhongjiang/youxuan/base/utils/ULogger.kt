package com.zhongjiang.youxuan.base.utils

import com.blankj.utilcode.util.LogUtils

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
    fun i(vararg anyMessage:Any?){
        LogUtils.i(anyMessage)
    }
    fun iTag(tag:String,vararg anyMessage:Any?){
        LogUtils.iTag(tag,anyMessage)
    }
    fun d(vararg any: Any?){
        LogUtils.d(any)
    }
    fun dTag(tag:String,vararg any: Any?){
        LogUtils.dTag(tag,any)
    }
    fun e(vararg any: Any?){
        LogUtils.e(any)
    }
    fun eTag(tag: String,vararg any: Any?){
        LogUtils.eTag(tag,any)
    }
    fun w(vararg any: Any?){
        LogUtils.w(any)
    }
    fun wTag(tag: String,vararg any: Any?){
        LogUtils.wTag(tag,any)
    }
    fun v(vararg any: Any?){
        LogUtils.v(any)
    }
    fun vTag(tag: String,vararg any: Any?){
        LogUtils.v(tag,any)
    }
    fun json(jsonStr: Any){
        LogUtils.json(jsonStr)
    }
    fun xml(xml: String){
        LogUtils.xml(xml)
    }
}