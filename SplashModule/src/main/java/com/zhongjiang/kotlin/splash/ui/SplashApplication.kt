package com.zhongjiang.kotlin.splash.ui

import com.zhongjiang.kotlin.user.injection.component.DaggerSplashModuleComponent
import com.zhongjiang.youxuan.base.common.BaseApplication
import com.zhongjiang.youxuan.user.splash.BuildConfig
import me.yokeyword.fragmentation.Fragmentation

class SplashApplication : BaseApplication() {

    override fun initAppInjection() {
        DaggerSplashModuleComponent.builder().appModule(getAppModule())
                .httpClientModule(getHttpClientModule())
                .globalConfigModule(getGlobalConfigModule())
                .cacheModule(getCacheModule()).build()
                .inject(this)

        Fragmentation.builder().debug(BuildConfig.DEBUG).stackViewMode(Fragmentation.BUBBLE).install()
    }

}