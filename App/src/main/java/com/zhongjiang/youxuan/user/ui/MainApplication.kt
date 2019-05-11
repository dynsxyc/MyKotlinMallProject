package com.zhongjiang.youxuan.user.ui

import com.zhongjiang.youxuan.base.common.BaseApplication
import com.zhongjiang.youxuan.user.splash.BuildConfig
import com.zhongjiang.youxuan.user.injection.component.DaggerMainModuleComponent
import me.yokeyword.fragmentation.Fragmentation

class MainApplication : BaseApplication() {
    override fun initAppInjection() {
        DaggerMainModuleComponent.builder().appModule(getAppModule())
                .httpClientModule(getHttpClientModule())
                .globalConfigModule(getGlobalConfigModule())
                .cacheModule(getCacheModule()).build()
                .inject(this)

        Fragmentation.builder().debug(BuildConfig.DEBUG).stackViewMode(Fragmentation.BUBBLE).install()
    }

}