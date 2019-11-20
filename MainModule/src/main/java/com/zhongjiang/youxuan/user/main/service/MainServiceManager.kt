package com.zhongjiang.kotlin.splash.service

import com.zhongjiang.kotlin.user.data.api.MainService
import com.zhongjiang.youxuan.base.data.net.service.BaseServiceManager
import com.zhongjiang.hotel.base.injection.scope.ActivityScope
import javax.inject.Inject

@com.zhongjiang.hotel.base.injection.scope.ActivityScope
class MainServiceManager @Inject constructor(val splashService: MainService): BaseServiceManager {
    override fun destroy() {

    }
}