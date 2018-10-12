package com.zhongjiang.kotlin.base.injection.component

import android.content.Context
import com.zhongjiang.kotlin.base.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by dyn on 2018/7/17.
 */
@Component(modules = arrayOf(AppModule::class))
@Singleton
interface AppComponent {
    fun context():Context
}