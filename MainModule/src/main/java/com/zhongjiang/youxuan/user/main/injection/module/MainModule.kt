package com.zhongjiang.kotlin.user.injection.module

import com.zhongjiang.kotlin.splash.injection.module.MainActivityModule
import com.zhongjiang.kotlin.splash.injection.module.SplashFragmentModule
import com.zhongjiang.kotlin.splash.injection.module.MainModuleServiceModule
import com.zhongjiang.kotlin.splash.ui.fragment.TabMainFragment
import com.zhongjiang.youxuan.base.injection.component.BaseActivityComponent
import com.zhongjiang.youxuan.base.injection.component.BaseFragmentComponent
import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.user.main.ui.activity.MainSingleFragmentActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @date on 2019/05/08 09:04
 * @packagename
 * @author dyn
 * @org com.zhongjiang.youxuan
 * @describe 模块对外暴露的Module
 * @email 583454199@qq.com
 **/
@Module(subcomponents = [BaseFragmentComponent::class, BaseActivityComponent::class],includes = [MainModuleServiceModule::class])
abstract class MainModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashFragmentModule::class])
    abstract fun contributeSplashFragmentInjector():TabMainFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeSplashActivityInjector(): MainSingleFragmentActivity

}