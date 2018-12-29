package com.zhongjiang.kotlin.user.injection.component

import com.google.gson.Gson
import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.injection.module.*
import com.zhongjiang.kotlin.user.injection.module.SplashModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient

/**
 * Created by dyn on 2018/7/16.
 */
@javax.inject.Singleton
@Component(modules = arrayOf(AndroidInjectionModule ::class,
        AndroidSupportInjectionModule::class,
        GlobalConfigModule::class,
        AppModule::class,
        HttpClientModule::class,
        BaseBoxStoreModule::class,
        GlobalServiceModule::class,
        CacheModule::class,
        SplashModule::class
        ))
interface SplashModuleComponent {
    fun gson():Gson
    fun baseApplication():BaseApplication
    fun okHttpClient(): OkHttpClient
    fun inject(baseApplication: BaseApplication)
}