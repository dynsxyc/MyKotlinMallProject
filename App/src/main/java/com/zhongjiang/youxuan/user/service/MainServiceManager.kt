package com.zhongjiang.youxuan.user.splash.service

import com.zhongjiang.youxuan.base.data.net.service.BaseServiceManager
import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.user.data.api.MainService
import javax.inject.Inject

@ActivityScope
class MainServiceManager @Inject constructor(val mainService: MainService):BaseServiceManager {
    override fun destroy() {

    }
}