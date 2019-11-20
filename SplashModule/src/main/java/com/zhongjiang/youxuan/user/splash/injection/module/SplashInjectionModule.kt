package com.zhongjiang.youxuan.user.injection.module

import com.zhongjiang.youxuan.base.injection.component.BaseActivityComponent
import com.zhongjiang.youxuan.base.injection.component.BaseFragmentComponent
import com.zhongjiang.hotel.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.user.splash.injection.module.SplashViewModule
import com.zhongjiang.youxuan.user.splash.injection.module.SplashServiceModule
import com.zhongjiang.youxuan.user.splash.ui.activity.splash.SplashActivity
import com.zhongjiang.youxuan.user.splash.ui.fragment.login.LoginFragment
import com.zhongjiang.youxuan.user.splash.ui.fragment.splash.SplashFragment
import com.zhongjiang.youxuan.user.splash.ui.fragment.testpicture.TestPictureFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by dyn on 2018/7/16.
 */
@Module(subcomponents = [BaseFragmentComponent::class, BaseActivityComponent::class],includes = [SplashServiceModule::class])
abstract class SplashInjectionModule {
    @com.zhongjiang.hotel.base.injection.scope.ActivityScope
    @ContributesAndroidInjector(modules = [SplashViewModule::class])
    abstract fun contributeSplashFragmentInjector(): SplashFragment

    @com.zhongjiang.hotel.base.injection.scope.ActivityScope
    @ContributesAndroidInjector(modules = [SplashViewModule::class])
    abstract fun contributeLoginFragmentInjector(): LoginFragment

    @com.zhongjiang.hotel.base.injection.scope.ActivityScope
    @ContributesAndroidInjector(modules = [SplashViewModule::class])
    abstract fun contributeTestPictureFragmentInjector(): TestPictureFragment

    @com.zhongjiang.hotel.base.injection.scope.ActivityScope
    @ContributesAndroidInjector(modules = [SplashViewModule::class])
    abstract fun contributeSplashActivityInjector(): SplashActivity

}