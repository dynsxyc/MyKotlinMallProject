package com.zhongjiang.kotlin.base.presenter

import android.util.Log
import com.zhongjiang.kotlin.base.data.net.cache.CacheProviders
import com.zhongjiang.kotlin.base.data.net.service.BaseServiceManager
import com.zhongjiang.kotlin.base.data.net.service.GlobalServiceManager
import com.zhongjiang.kotlin.base.ext.handlerThread
import com.zhongjiang.kotlin.base.injection.module.sheduler.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by QingMei on 2017/8/14.
 * desc:
 */

open class BaseModel<S : BaseServiceManager> constructor(serviceManager: S) : IModel {

    var serviceManager: S

    /**
     * Retrofit Service Manager
     */
    init {
        this.serviceManager = serviceManager
    }

    @Inject
    lateinit var cacheProviders: CacheProviders

    @Inject
    lateinit var schedulers: SchedulerProvider

    @Inject
    lateinit var globalServiceManager: GlobalServiceManager

    /**开始倒计时功能*/
    override fun startTimer(number: Long, onNext: Consumer<Long>, onComplete: Action): Flowable<Long> {
        return Flowable.intervalRange(0, number, 0, 1, TimeUnit.SECONDS).handlerThread(schedulers).doOnNext (onNext).doOnComplete(onComplete).doOnError {
            it.printStackTrace()
            Log.i("test1","onTimer 异常")
        }
    }
    /**全局公共接口调用--------------------------------start-------------GlobalServiceManager中的接口-------------------*/
    /**获取验证码*/
    override fun requestRegCode() {

    }

    /**全局公共接口调用--------------------------------end-------------GlobalServiceManager中的接口-------------------*/


    override fun onDestroy() {
    }
}
