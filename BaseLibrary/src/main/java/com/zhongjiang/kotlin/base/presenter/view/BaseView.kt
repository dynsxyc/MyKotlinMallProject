package com.zhongjiang.kotlin.base.presenter.view

/**
 * Created by dyn on 2018/7/13.
 */
interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError()
}