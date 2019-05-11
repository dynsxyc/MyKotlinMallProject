package com.zhongjiang.youxuan.base.injection.module

import com.zhongjiang.youxuan.base.injection.component.BaseActivityComponent
import com.zhongjiang.youxuan.base.injection.component.BaseFragmentComponent
import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.base.ui.activity.WebShowActivity
import com.zhongjiang.youxuan.base.ui.fragment.YXWebShowFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(subcomponents = [BaseActivityComponent::class,BaseFragmentComponent::class])
abstract class BaseModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [BaseUIModule::class])
    abstract fun contributeBaseWebFragmentInjector(): YXWebShowFragment
    @ActivityScope
    @ContributesAndroidInjector(modules = [BaseUIModule::class])
    abstract fun contributeWebShowActivityInjector(): WebShowActivity
}