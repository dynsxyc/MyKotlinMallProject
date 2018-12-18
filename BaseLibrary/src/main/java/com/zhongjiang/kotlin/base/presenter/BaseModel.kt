package com.zhongjiang.kotlin.base.presenter

import com.zhongjiang.kotlin.base.data.net.cache.CacheProviders
import com.zhongjiang.kotlin.base.data.net.service.BaseServiceManager
import com.zhongjiang.kotlin.base.data.net.service.GlobalServiceManager
import com.zhongjiang.kotlin.base.injection.module.sheduler.SchedulerProvider
import javax.inject.Inject

/**
 * Created by QingMei on 2017/8/14.
 * desc:
 */

open class BaseModel<S : BaseServiceManager> constructor(serviceManager: S) : IModel {

    var serviceManager:S
    /**
     * Retrofit Service Manager
     */
    init {
        this.serviceManager = serviceManager
    }

    @Inject
    protected lateinit var cacheProviders: CacheProviders

    @Inject
    lateinit var schedulers: SchedulerProvider

    @Inject
    lateinit var globalServiceManager: GlobalServiceManager

    override fun onDestroy() {
    }
}
