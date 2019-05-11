package com.zhongjiang.youxuan.base.presenter

import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

interface IModel {
    fun startTimer(number: Long, onNext: Consumer<Long>, onComplete: Action): Flowable<Long>
    fun requestRegCode()
    fun onDestroy()
}