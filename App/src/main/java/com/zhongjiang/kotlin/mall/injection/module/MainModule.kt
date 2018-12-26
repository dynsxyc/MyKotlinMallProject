package com.zhongjiang.kotlin.user.injection.module

import com.zhongjiang.kotlin.base.injection.component.BaseActivityComponent
import com.zhongjiang.kotlin.base.injection.component.BaseFragmentComponent
import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.mall.ui.activity.MainActivity
import com.zhongjiang.kotlin.splash.injection.module.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by dyn on 2018/7/16.
 */
@Module(subcomponents = arrayOf(BaseFragmentComponent::class,BaseActivityComponent::class),includes = arrayOf(MainServiceModule::class))
abstract class MainModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contributeSplashActivityInjector():MainActivity

}