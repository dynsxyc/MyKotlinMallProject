package com.zhongjiang.kotlin.splash.service

import com.zhongjiang.kotlin.user.data.api.MainService
import com.zhongjiang.youxuan.base.data.net.service.BaseServiceManager
import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import javax.inject.Inject

@ActivityScope
class SplashServiceManager @Inject constructor(val splashService: MainService): BaseServiceManager {
    override fun destroy() {

    }
}