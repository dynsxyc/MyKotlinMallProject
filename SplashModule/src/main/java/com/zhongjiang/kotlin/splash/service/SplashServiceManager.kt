package com.zhongjiang.kotlin.splash.service

import com.zhongjiang.kotlin.base.data.net.service.BaseServiceManager
import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.user.data.api.SplashService
import javax.inject.Inject

@ActivityScope
class SplashServiceManager @Inject constructor(val splashService: SplashService):BaseServiceManager {
    override fun destroy() {

    }
}