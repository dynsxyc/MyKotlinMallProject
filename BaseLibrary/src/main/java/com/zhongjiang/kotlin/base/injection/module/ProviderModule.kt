package com.zhongjiang.kotlin.base.injection.module

import com.zhongjiang.kotlin.base.injection.component.BaseActivityComponent
import com.zhongjiang.kotlin.base.injection.component.BaseFragmentComponent
import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.base.ui.fragment.YXWebShowFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(subcomponents = [BaseActivityComponent::class,BaseFragmentComponent::class])
abstract class ProviderModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [ProviderUIModule::class])
    abstract fun contributeBaseWebFragmentInjector(): YXWebShowFragment
}