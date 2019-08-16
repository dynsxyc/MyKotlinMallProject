package com.zhongjiang.kotlin.splash.service

import com.zhongjiang.kotlin.user.data.api.SplashService
import com.zhongjiang.youxuan.base.data.net.service.BaseServiceManager
import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import javax.inject.Inject

@ActivityScope
class SplashServiceManager @Inject constructor(val splashService: SplashService): BaseServiceManager {
    override fun destroy() {

    }
}