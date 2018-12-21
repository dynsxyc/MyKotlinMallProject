package com.zhongjiang.kotlin.base.rx

import android.util.Log
import com.zhongjiang.kotlin.base.presenter.IView
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable

/**
 * Created by dyn on 2018/7/13.
 * 订阅
 */
open class BaseMaybeObserver<T>(val iView: IView) : MaybeObserver<T> {
    override fun onError(e: Throwable) {
        e.printStackTrace()
        iView.hideLoading()
        if (e is BaseException){
            iView.onError(e.msg)
        }
        Log.i("test1","onError")
    }

    override fun onSuccess(t: T) {
        Log.i("test1","onSuccess")
    }

    override fun onComplete() {
        Log.i("test1","onComplete")
        iView.hideLoading()
    }

    override fun onSubscribe(d: Disposable) {
    }

}