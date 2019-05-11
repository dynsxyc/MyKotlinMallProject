package com.zhongjiang.youxuan.user.injection.module

import com.zhongjiang.youxuan.base.injection.component.BaseActivityComponent
import com.zhongjiang.youxuan.base.injection.component.BaseFragmentComponent
import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.user.splash.injection.module.LoginFragmentModule
import com.zhongjiang.youxuan.user.splash.injection.module.SplashActivityModule
import com.zhongjiang.youxuan.user.splash.injection.module.SplashFragmentModule
import com.zhongjiang.youxuan.user.splash.injection.module.SplashServiceModule
import com.zhongjiang.youxuan.user.splash.ui.activity.SplashActivity
import com.zhongjiang.youxuan.user.splash.ui.fragment.LoginFragment
import com.zhongjiang.youxuan.user.splash.ui.fragment.SplashFragment
import com.zhongjiang.youxuan.user.splash.ui.fragment.TestPictureFragment
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
    @ContributesAndroidInjector(modules = [LoginFragmentModule::class])
    abstract fun contributeTestPictureFragmentInjector():TestPictureFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun contributeSplashActivityInjector():SplashActivity

}