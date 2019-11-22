package com.zhongjiang.hotel.base.rx

import android.util.Log
import com.zhongjiang.hotel.base.ui.basemvp.IView
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable

/**
 * Created by dyn on 2018/7/13.
 * 订阅
 */
open class BaseMaybeObserver<T>(private val view: IView<*>) : MaybeObserver<T> {
    override fun onError(e: Throwable) {
        e.printStackTrace()
        view.hideLoading()
        var responeThrowable = ExceptionHandle.handleException(e)
        view.onNetError(responeThrowable.status, responeThrowable.msg)
        Log.i("test1", "onNetError")
    }

    override fun onSuccess(t: T) {
        view.hideLoading()
        Log.i("test1", "onSuccess")
    }

    override fun onComplete() {
        Log.i("test1", "onComplete")
        view.hideLoading()
    }

    override fun onSubscribe(d: Disposable) {
    }

}