package com.zhongjiang.hotel.main.ui

import com.zhongjiang.hotel.base.common.BaseApplication
import com.zhongjiang.hotel.main.BuildConfig
import me.yokeyword.fragmentation.Fragmentation

class MainApplication : BaseApplication() {

    override fun initAppInjection() {
        DaggerMainModuleComponent.builder().appModule(getAppModule())
                .globalConfigModule(getGlobalConfigModule())
                .httpClientModule(getHttpClientModule())
                .cacheModule(getCacheModule()).build()
                .inject(this)

        Fragmentation.builder().debug(BuildConfig.DEBUG).stackViewMode(Fragmentation.BUBBLE).install()
    }

}