package com.zhongjiang.hotel.base.ui.basemvp

import com.zhongjiang.hotel.base.data.net.cache.CacheProviders
import com.zhongjiang.hotel.base.data.net.service.BaseServiceManager
import com.zhongjiang.hotel.base.ext.handlerThread
import com.zhongjiang.hotel.base.injection.module.sheduler.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by QingMei on 2017/8/14.
 * desc:
 */

abstract class BaseModel<S : BaseServiceManager>  : IModel {
    @Inject
    lateinit var serviceManager: S

    @Inject
    lateinit var cacheProviders: CacheProviders

    @Inject
    lateinit var schedulers: SchedulerProvider

    /**开始倒计时功能*/
    override fun startTimer(number: Long, onNext: Consumer<Long>, onComplete: Action): Flowable<Long> {
        return Flowable.intervalRange(0, number, 0, 1, TimeUnit.SECONDS).handlerThread(schedulers).doOnNext (onNext).doOnComplete(onComplete).doOnError {
            it.printStackTrace()
        }
    }

    override fun onDestroy() {
        serviceManager.destroy()
    }
}
