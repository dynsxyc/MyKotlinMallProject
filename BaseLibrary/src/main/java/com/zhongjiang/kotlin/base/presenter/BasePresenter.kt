package com.zhongjiang.kotlin.base.presenter

import android.arch.lifecycle.LifecycleOwner
import android.support.annotation.CallSuper
import android.support.annotation.MainThread
import com.uber.autodispose.AutoDisposeConverter
import com.zhongjiang.kotlin.base.NetWorkUtils
import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.utils.RxLifecycleUtils
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
open class BasePresenter<V : IView,M:IModel>  constructor(view:V,model:M): IPresenter {

    //    1. lateinit 延迟加载
//    2.lateinit 只能修饰, 非kotlin基本类型
//    因为Kotlin会使用null来对每一个用lateinit修饰的属性做初始化，而基础类型是没有null类型，所以无法使用lateinit。
    var mView = view

    var mModel = model
    @Inject
    lateinit var context: BaseApplication

    lateinit var lifecycleProvider: LifecycleOwner

//    fun const(view:V,model:M){
//        mView = view
//        mModel = model
//    }

    fun checkNetWork(): Boolean {
        if (NetWorkUtils.isNetWorkAvailable(context)) {
            return true
        }
        mView.onError("网络不可用")
        return false
    }

    fun <T> bindLifecycle(): AutoDisposeConverter<T> {
        return RxLifecycleUtils.bindLifecycle(lifecycleProvider)
    }

    override fun setLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        this.lifecycleProvider = lifecycleOwner
    }
    @MainThread @CallSuper
    override fun onCreate(owner: LifecycleOwner) {
    }
    @MainThread @CallSuper
    override fun onStart(owner: LifecycleOwner) {
    }
    @MainThread @CallSuper
    override fun onResume(owner: LifecycleOwner) {
    }
    @MainThread @CallSuper
    override fun onPause(owner: LifecycleOwner) {
    }

    @MainThread @CallSuper
    override fun onStop(owner: LifecycleOwner) {
    }

    @CallSuper
    @MainThread
    override fun onDestroy(owner: LifecycleOwner) {
            mModel.onDestroy()
    }

    @CallSuper
    @MainThread
    override fun onLifecycleChanged(owner: LifecycleOwner) {
    }

}