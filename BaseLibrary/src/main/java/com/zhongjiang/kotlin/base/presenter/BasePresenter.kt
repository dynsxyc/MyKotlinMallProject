package com.zhongjiang.kotlin.base.presenter

import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import com.orhanobut.logger.Logger
import com.uber.autodispose.ScopeProvider
import com.zhongjiang.kotlin.base.NetWorkUtils
import com.zhongjiang.kotlin.base.busevent.ActivityResultEvent
import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.data.db.UserInfoEntity
import com.zhongjiang.kotlin.base.utils.RxBus
import com.zhongjiang.kotlin.base.utils.RxLifecycleUtils
import io.objectbox.Box
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by dyn on 2018/7/13.
 */
open class BasePresenter<V : IView, M : IModel> constructor(view: V, model: M) : IPresenter {
    //    1. lateinit 延迟加载
//    2.lateinit 只能修饰, 非kotlin基本类型
//    因为Kotlin会使用null来对每一个用lateinit修饰的属性做初始化，而基础类型是没有null类型，所以无法使用lateinit。
    var mView = view

    var mModel = model
    @Inject
    lateinit var context: BaseApplication

    @Inject
    @Singleton
    lateinit var mRxBus: RxBus

    @Inject
    lateinit var mUserInfoEntity: Box<UserInfoEntity>

    fun checkNetWork(): Boolean {
        if (NetWorkUtils.isNetWorkAvailable(context)) {
            return true
        }
        mView.onError("网络不可用")
        return false
    }

    lateinit var lifecycleProvider: LifecycleOwner

    fun bindLifecycle(): ScopeProvider {
        return RxLifecycleUtils.bindLifecycle(lifecycleProvider)
    }

    fun bindBusLifecycle(): ScopeProvider {
        return RxLifecycleUtils.bindBusLifecycle(lifecycleProvider)
    }

    override fun setLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        this.lifecycleProvider = lifecycleOwner
    }

    @MainThread
    @CallSuper
    override fun onCreate(owner: LifecycleOwner) {
        Logger.d("onCreate")
    }

    @MainThread
    @CallSuper
    override fun onStart(owner: LifecycleOwner) {
        Logger.d("onStart")
    }

    @MainThread
    @CallSuper
    override fun onResume(owner: LifecycleOwner) {
        Logger.d("onResume")
    }

    @MainThread
    @CallSuper
    override fun onPause(owner: LifecycleOwner) {
        Logger.d("onPause")
    }

    @MainThread
    @CallSuper
    override fun onStop(owner: LifecycleOwner) {
        Logger.d("onStop")
    }

    @CallSuper
    @MainThread
    override fun onDestroy(owner: LifecycleOwner) {
        Logger.d("onDestroy")
        mModel.onDestroy()
    }

    @CallSuper
    @MainThread
    override fun onLifecycleChanged(owner: LifecycleOwner) {
    }

    fun registerActivityResultEvent(method: (ActivityResultEvent) -> Unit) {
        mRxBus.toObservable(ActivityResultEvent::class.java, Consumer {
            method(it)
            Logger.i("registerActivityResultEvent   调用成功")
        }, Consumer {
            it.printStackTrace()
            Logger.e("registerActivityResultEvent   error")
            Logger.e("registerActivityResultEvent   $it")
        }, Action {
            Logger.i("registerActivityResultEvent   oncomplete")
        }, Consumer {
            Logger.i("registerActivityResultEvent  isDisposed =  ${it.isDisposed}")
        }, bindBusLifecycle())
    }

}