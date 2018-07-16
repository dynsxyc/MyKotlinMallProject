package com.zhongjiang.kotlin.base.rx

import rx.Subscriber

/**
 * Created by dyn on 2018/7/13.
 */
open class BaseSubscriber<T> : Subscriber<T>() {
    override fun onCompleted() {
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable?) {
    }
}