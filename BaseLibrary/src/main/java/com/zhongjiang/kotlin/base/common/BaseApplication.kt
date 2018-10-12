package com.zhongjiang.kotlin.base.common

import android.app.Application
import android.content.Context
import com.zhongjiang.kotlin.base.injection.component.AppComponent
import com.zhongjiang.kotlin.base.injection.component.DaggerAppComponent
import com.zhongjiang.kotlin.base.injection.module.AppModule

/**
 * Created by dyn on 2018/7/17.
 */
class BaseApplication : Application() {
    lateinit var appComponent:AppComponent
    override fun onCreate() {
        super.onCreate()
        initAppInjection()
        context = this
    }

    private fun initAppInjection() {
        appComponent =   DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var  context:Context
    }
}