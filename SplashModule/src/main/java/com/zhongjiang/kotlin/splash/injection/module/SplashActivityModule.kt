package com.zhongjiang.kotlin.splash.injection.module

import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.splash.ui.activity.AppSplashActivity
import dagger.Module
import dagger.Provides

@Module
class SplashActivityModule {
    @Provides
    @ActivityScope
    fun provideSplashActivity(splashActivity: AppSplashActivity):AppSplashActivity{
        return splashActivity
    }

}