package com.zhongjiang.kotlin.base.ext

import com.zhongjiang.kotlin.base.rx.BaseSubscriber
import rx.Observable
import rx.schedulers.Schedulers

/**
 * Created by dyn on 2018/7/13.
 */
fun <T> Observable<T>.execute(subscriber: BaseSubscriber<T>) {
    this.observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(subscriber)
}