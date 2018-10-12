package com.zhongjiang.kotlin.base.presenter

import android.content.Context
import com.trello.rxlifecycle.LifecycleProvider
import com.zhongjiang.kotlin.base.NetWorkUtils
import com.zhongjiang.kotlin.base.presenter.view.BaseView
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
open class BasePresenter<T : BaseView> {
//    1. lateinit 延迟加载
//    2.lateinit 只能修饰, 非kotlin基本类型
//    因为Kotlin会使用null来对每一个用lateinit修饰的属性做初始化，而基础类型是没有null类型，所以无法使用lateinit。
    lateinit var mView : T
    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>
    @Inject
    lateinit var context:Context
    fun checkNetWork():Boolean{
        if (NetWorkUtils.isNetWorkAvailable(context)){
            return true
        }
        mView.onError("网络不可用")
        return false
    }
}