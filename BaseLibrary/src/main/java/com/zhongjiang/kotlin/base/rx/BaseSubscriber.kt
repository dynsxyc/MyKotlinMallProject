package com.zhongjiang.kotlin.base.rx

import com.zhongjiang.kotlin.base.presenter.IView
import rx.Subscriber

/**
 * Created by dyn on 2018/7/13.
 * 订阅
 */
open class BaseSubscriber<T>(val iView: IView) : Subscriber<T>() {
    override fun onCompleted() {
        iView.hideLoading()
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable?) {
        iView.hideLoading()
        if (e is BaseException){
            iView.onError(e.msg)
        }
    }
}