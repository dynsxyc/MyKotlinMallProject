package com.zhongjiang.kotlin.base.injection.component

import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.injection.module.AppModule
import com.zhongjiang.kotlin.base.injection.module.GlobalConfigModule
import com.zhongjiang.kotlin.base.injection.module.GlobalServiceModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by dyn on 2018/7/17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class,
                AndroidInjectionModule::class,
                AndroidSupportInjectionModule::class,
                GlobalConfigModule::class,
                GlobalServiceModule::class))
interface BaseAppComponent {
    fun context():BaseApplication
}