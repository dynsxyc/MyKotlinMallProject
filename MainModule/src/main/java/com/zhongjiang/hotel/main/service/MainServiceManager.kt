package com.zhongjiang.hotel.main.service

import com.zhongjiang.hotel.base.data.net.service.BaseServiceManager
import com.zhongjiang.hotel.base.injection.scope.ActivityScope
import com.zhongjiang.hotel.main.service.api.MainService
import javax.inject.Inject

@ActivityScope
class MainServiceManager @Inject constructor(val server: MainService) : BaseServiceManager {
    override fun destroy() {

    }
}