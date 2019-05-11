package com.zhongjiang.youxuan.user.splash.service

import com.zhongjiang.youxuan.base.data.net.service.BaseServiceManager
import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.user.data.api.SplashService
import javax.inject.Inject

@ActivityScope
class SplashServiceManager @Inject constructor(val splashService: SplashService):BaseServiceManager {
    override fun destroy() {

    }
}