package com.zhongjiang.kotlin.user.injection.component

import com.google.gson.Gson
import com.zhongjiang.kotlin.user.injection.module.MainModule
import com.zhongjiang.youxuan.base.common.BaseApplication
import com.zhongjiang.youxuan.base.injection.module.*
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient


/**
 * @date on 2019/05/08 08:56
 * @packagename 
 * @author dyn
 * @org com.zhongjiang.youxuan
 * @describe 模块对应的ModuleComponent
 * @email 583454199@qq.com
 **/
@javax.inject.Singleton
@Component(modules = [AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    GlobalConfigModule::class,
    AppModule::class,
    HttpClientModule::class,
    BaseBoxStoreModule::class,
    GlobalServiceModule::class,
    BaseModule::class,
    CacheModule::class,
    MainModule::class])
interface MainModuleComponent {
    fun gson(): Gson
    fun baseApplication(): BaseApplication
    fun okHttpClient(): OkHttpClient
    fun inject(baseApplication: BaseApplication)
}