package com.zhongjiang.kotlin.base.injection.component

import android.app.Activity
import android.content.Context
import com.trello.rxlifecycle.LifecycleProvider
import com.zhongjiang.kotlin.base.injection.ActivityScope
import com.zhongjiang.kotlin.base.injection.module.ActivityModule
import com.zhongjiang.kotlin.base.injection.module.LifecycleProviderModule
import dagger.Component

/**
 * Created by dyn on 2018/7/17.
 */
@Component(modules = arrayOf(ActivityModule::class,LifecycleProviderModule::class) ,dependencies = arrayOf(AppComponent::class))
@ActivityScope
interface ActivityComponent {
    fun activity(): Activity
    fun context():Context
    fun lifecycleProvider():LifecycleProvider<*>
}