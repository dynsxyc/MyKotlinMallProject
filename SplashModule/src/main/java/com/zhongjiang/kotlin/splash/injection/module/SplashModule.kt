package com.zhongjiang.kotlin.user.injection.module

import com.zhongjiang.kotlin.base.injection.component.BaseActivityComponent
import com.zhongjiang.kotlin.base.injection.component.BaseFragmentComponent
import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.splash.injection.module.LoginFragmentModule
import com.zhongjiang.kotlin.splash.injection.module.SplashActivityModule
import com.zhongjiang.kotlin.splash.injection.module.SplashFragmentModule
import com.zhongjiang.kotlin.splash.injection.module.SplashServiceModule
import com.zhongjiang.kotlin.splash.ui.activity.SplashActivity
import com.zhongjiang.kotlin.splash.ui.fragment.LoginFragment
import com.zhongjiang.kotlin.splash.ui.fragment.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by dyn on 2018/7/16.
 */
@Module(subcomponents = [BaseFragmentComponent::class, BaseActivityComponent::class],includes = [SplashServiceModule::class])
abstract class SplashModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashFragmentModule::class])
    abstract fun contributeSplashFragmentInjector():SplashFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginFragmentModule::class])
    abstract fun contributeLoginFragmentInjector():LoginFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun contributeSplashActivityInjector():SplashActivity

}