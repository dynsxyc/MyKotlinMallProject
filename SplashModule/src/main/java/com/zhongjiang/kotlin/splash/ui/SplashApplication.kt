package com.zhongjiang.kotlin.splash.ui

import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.splash.BuildConfig
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