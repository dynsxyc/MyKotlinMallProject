package com.zhongjiang.kotlin.base.rx

import com.zhongjiang.kotlin.base.presenter.view.BaseView
import rx.Subscriber

/**
 * Created by dyn on 2018/7/13.
 * 订阅
 */
open class BaseSubscriber<T>(val baseView: BaseView) : Subscriber<T>() {
    override fun onCompleted() {
        baseView.hideLoading()
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable?) {
        baseView.hideLoading()
        if (e is BaseException){
            baseView.onError(e.msg)
        }
    }
}