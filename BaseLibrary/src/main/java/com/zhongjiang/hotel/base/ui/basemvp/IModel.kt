package com.zhongjiang.hotel.base.ui.basemvp

import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

interface IModel {
    fun startTimer(number: Long, next: Consumer<Long>, onComplete: Action): Flowable<Long>
    fun onDestroy()
}