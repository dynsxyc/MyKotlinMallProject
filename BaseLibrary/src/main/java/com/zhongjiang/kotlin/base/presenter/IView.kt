package com.zhongjiang.kotlin.base.presenter

/**
 * Created by dyn on 2018/7/13.
 */
interface IView {
    fun showLoading()
    fun hideLoading()
    fun onError(text:String)
}