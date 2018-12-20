package com.zhongjiang.kotlin.splash.ui

import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.user.injection.component.DaggerSplashModuleComponent

class SplashApplication : BaseApplication() {

    override fun initAppInjection() {
        DaggerSplashModuleComponent.builder().appModule(getAppModule())
                .httpClientModule(getHttpClientModule())
                .globalConfigModule(getGlobalConfigModule())
                .cacheModule(getCacheModule()).build()
                .inject(this)
    }
}