package com.zhongjiang.kotlin.user.injection.component

import com.google.gson.Gson
import com.zhongjiang.hotel.base.common.BaseApplication
import com.zhongjiang.hotel.base.injection.module.AppModule
import com.zhongjiang.hotel.base.injection.module.CacheModule
import com.zhongjiang.hotel.base.injection.module.GlobalConfigModule
import com.zhongjiang.hotel.base.injection.module.HttpClientModule
import com.zhongjiang.kotlin.user.injection.module.MainModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
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
    GlobalConfigModule::class,
    AppModule::class,
    HttpClientModule::class,
    CacheModule::class,
    MainModule::class])
interface MainModuleComponent {
    fun gson(): Gson
    fun baseApplication(): BaseApplication
    fun okHttpClient(): OkHttpClient
    fun inject(baseApplication: BaseApplication)
}