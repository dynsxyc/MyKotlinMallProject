package com.zhongjiang.youxuan.user.injection.component

import com.google.gson.Gson
import com.zhongjiang.youxuan.base.common.BaseApplication
import com.zhongjiang.youxuan.base.injection.module.*
import com.zhongjiang.youxuan.user.injection.module.MainModule
import com.zhongjiang.youxuan.user.injection.module.SplashModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by dyn on 2018/7/16.
 */
@Singleton
@Component(modules = arrayOf(AndroidInjectionModule ::class,
        AndroidSupportInjectionModule::class,
        GlobalConfigModule::class,
        AppModule::class,
        HttpClientModule::class,
        BaseBoxStoreModule::class,
        GlobalServiceModule::class,
        BaseModule::class,
        CacheModule::class,
        SplashModule::class,
        MainModule::class
        ))
interface MainModuleComponent {
    fun gson():Gson
    fun baseApplication():BaseApplication
    fun okHttpClient(): OkHttpClient
    fun inject(baseApplication: BaseApplication)
}