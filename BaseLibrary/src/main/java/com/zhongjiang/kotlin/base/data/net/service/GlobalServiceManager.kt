package com.zhongjiang.kotlin.base.data.net.service

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalServiceManager @Inject
constructor(userInfoService: GlobalService) : BaseServiceManager {

    var userInfoService: GlobalService = userInfoService

    override fun destroy() {
    }
}
