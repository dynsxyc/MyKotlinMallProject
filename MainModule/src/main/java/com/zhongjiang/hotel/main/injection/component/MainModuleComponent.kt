package com.zhongjiang.hotel.main.injection.component


import com.zhongjiang.hotel.base.common.BaseApplication
import com.zhongjiang.hotel.base.injection.module.AppModule
import com.zhongjiang.hotel.base.injection.module.CacheModule
import com.zhongjiang.hotel.base.injection.module.GlobalConfigModule
import com.zhongjiang.hotel.base.injection.module.HttpClientModule
import com.zhongjiang.hotel.main.injection.module.MainModule
import com.zhongjiang.hotel.provider.injection.BoxStoreModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
/**
 * @date on 2019/05/08 08:56
 * @packagename
 * @author dyn
 * @org com.zhongjiang.youxuan
 * @describe 模块对应的ModuleComponent
 * @email 583454199@qq.com
 **/
@Singleton
@Component(modules = [AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    AppModule::class,
    GlobalConfigModule::class,
    HttpClientModule::class,
    BoxStoreModule::class,
    CacheModule::class,
    MainModule::class])
interface MainModuleComponent {
    fun inject(baseApplication: BaseApplication)
}

