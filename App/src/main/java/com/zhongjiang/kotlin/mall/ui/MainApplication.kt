package com.zhongjiang.kotlin.mall.ui

import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.splash.BuildConfig
import com.zhongjiang.kotlin.user.injection.component.DaggerMainModuleComponent
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