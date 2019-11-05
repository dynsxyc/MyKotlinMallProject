package com.zhongjiang.youxuan.base.presenter

import android.app.Activity
import androidx.annotation.StringRes
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
interface IView<out P :IPresenter<IView<P>>>{
    val mPresenter:P
    fun getHostActivity():Activity
    fun showToast(message:String)
    fun showToast(@StringRes resId:Int)
    fun showLoading()
    fun hideLoading()
    fun onError(status:Int,text:String)
}