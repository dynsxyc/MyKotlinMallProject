package com.zhongjiang.hotel.base.data.net.service

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalServiceManager @Inject
constructor(val gloableService: GlobalService) : BaseServiceManager {
    override fun destroy() {
    }
    /**全局公共接口调用--------------------------------start-------------GlobalServiceManager中的接口-------------------*/
    /**获取验证码*/
    fun requestRegCode() {
    }

    /**全局公共接口调用--------------------------------end-------------GlobalServiceManager中的接口-------------------*/
}
