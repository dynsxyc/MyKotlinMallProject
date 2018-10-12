package com.zhongjiang.kotlin.user.injection.component

import com.zhongjiang.kotlin.base.injection.PreCommponentScope
import com.zhongjiang.kotlin.base.injection.component.ActivityComponent
import com.zhongjiang.kotlin.splash.ui.fragment.SplashFragment
import com.zhongjiang.kotlin.user.injection.module.SplashModule
import dagger.Component

/**
 * Created by dyn on 2018/7/16.
 */
@Component(modules = arrayOf(SplashModule ::class),dependencies = arrayOf(ActivityComponent::class))
@PreCommponentScope
interface SplashComponent {
    fun inject(fragment: SplashFragment)
}