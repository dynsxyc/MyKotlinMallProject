package com.zhongjiang.kotlin.base.injection.module

import com.trello.rxlifecycle.LifecycleProvider
import com.zhongjiang.kotlin.base.injection.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Created by dyn on 2018/7/17.
 */
@Module
class LifecycleProviderModule(private val lifecycleProvider: LifecycleProvider<*>) {
    @Provides
    @ActivityScope
    fun providesLifecycleProvider(): LifecycleProvider<*> {
        return lifecycleProvider
    }
}