package com.zhongjiang.youxuan.base.presenter

/**
 * Created by dyn on 2018/7/13.
 */
interface IView<out Presenter :IPresenter<IView<Presenter>>>{
    val presenter:Presenter
    fun showLoading()
    fun hideLoading()
    fun onError(status:Int,text:String)
}