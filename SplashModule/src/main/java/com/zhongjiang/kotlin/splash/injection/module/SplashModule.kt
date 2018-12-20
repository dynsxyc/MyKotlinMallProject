package com.zhongjiang.kotlin.user.injection.module

import com.zhongjiang.kotlin.base.injection.component.BaseActivityComponent
import com.zhongjiang.kotlin.base.injection.component.BaseFragmentComponent
import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.splash.injection.module.SplashActivityModule
import com.zhongjiang.kotlin.splash.injection.module.SplashFragmentModule
import com.zhongjiang.kotlin.splash.injection.module.SplashServiceModule
import com.zhongjiang.kotlin.splash.ui.activity.AppSplashActivity
import com.zhongjiang.kotlin.splash.ui.fragment.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by dyn on 2018/7/16.
 */
@Module(subcomponents = arrayOf(BaseFragmentComponent::class,BaseActivityComponent::class),includes = arrayOf(SplashServiceModule::class))
abstract class SplashModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(SplashFragmentModule::class))
    abstract fun contributeSplashFragmentInjector():SplashFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(SplashActivityModule::class))
    abstract fun contributeSplashActivityInjector():AppSplashActivity

}