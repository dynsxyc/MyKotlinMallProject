package com.zhongjiang.youxuan.base.ui.basemvp

import android.app.Activity
import androidx.annotation.StringRes

/**
 * Created by dyn on 2018/7/13.
 */
interface IView<out P : IPresenter<IView<P>>>{
    fun getHostActivity():Activity
    fun showToast(message:String)
    fun showToast(@StringRes resId:Int)
    fun showLoading()
    fun hideLoading()
    fun onNetError(status:Int, text:String)
}