package com.zhongjiang.youxuan.user.main.ui;
import com.zhongjiang.kotlin.user.injection.component.DaggerMainModuleComponent
import com.zhongjiang.youxuan.base.common.BaseApplication
import com.zhongjiang.youxuan.user.main.BuildConfig
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