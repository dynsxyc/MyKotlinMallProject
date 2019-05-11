package com.zhongjiang.youxuan.user.splash.ui

import com.zhongjiang.youxuan.base.common.BaseApplication
import com.zhongjiang.youxuan.user.splash.BuildConfig
import com.zhongjiang.youxuan.user.injection.component.DaggerSplashModuleComponent
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