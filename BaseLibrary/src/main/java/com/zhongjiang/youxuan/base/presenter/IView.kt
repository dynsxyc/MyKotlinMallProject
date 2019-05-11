package com.zhongjiang.youxuan.base.presenter

/**
 * Created by dyn on 2018/7/13.
 */
interface IView {
    fun showLoading()
    fun hideLoading()
    fun onError(status:Int,text:String)
}