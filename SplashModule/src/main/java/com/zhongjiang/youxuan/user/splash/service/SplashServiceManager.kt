package com.zhongjiang.youxuan.user.splash.service

import com.zhongjiang.youxuan.base.data.net.service.BaseServiceManager
import com.zhongjiang.hotel.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.user.data.api.SplashService
import javax.inject.Inject

@com.zhongjiang.hotel.base.injection.scope.ActivityScope
class SplashServiceManager @Inject constructor(val splashService: SplashService):BaseServiceManager {
    override fun destroy() {

    }
}