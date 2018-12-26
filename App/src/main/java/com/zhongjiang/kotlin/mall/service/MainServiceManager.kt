package com.zhongjiang.kotlin.splash.service

import com.zhongjiang.kotlin.base.data.net.service.BaseServiceManager
import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.user.data.api.MainService
import javax.inject.Inject

@ActivityScope
class MainServiceManager @Inject constructor(val mainService: MainService):BaseServiceManager {
    override fun destroy() {

    }
}