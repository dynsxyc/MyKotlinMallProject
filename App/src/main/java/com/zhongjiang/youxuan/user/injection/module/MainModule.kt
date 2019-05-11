package com.zhongjiang.youxuan.user.injection.module

import com.zhongjiang.youxuan.base.injection.component.BaseActivityComponent
import com.zhongjiang.youxuan.base.injection.component.BaseFragmentComponent
import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.user.ui.activity.MainActivity
import com.zhongjiang.youxuan.user.ui.activity.TestStatusBarActivity
import com.zhongjiang.youxuan.user.ui.fragment.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by dyn on 2018/7/16.
 */
@Module(subcomponents = arrayOf(BaseFragmentComponent::class,BaseActivityComponent::class),includes = arrayOf(MainServiceModule::class))
abstract class MainModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contributeMainActivityInjector():MainActivity
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contributeTestStatusBarActivityInjector():TestStatusBarActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainFragmentModule::class))
    abstract fun contributeMainFragmentInjector():MainFragment

}