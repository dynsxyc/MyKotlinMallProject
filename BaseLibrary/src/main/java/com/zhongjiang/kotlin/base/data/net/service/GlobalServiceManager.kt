package com.zhongjiang.kotlin.base.data.net.service

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalServiceManager @Inject
constructor(val userInfoService: GlobalService) : BaseServiceManager {
    override fun destroy() {
    }
}
