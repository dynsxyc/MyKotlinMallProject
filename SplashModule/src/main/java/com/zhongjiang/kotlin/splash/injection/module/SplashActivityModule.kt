package com.zhongjiang.kotlin.splash.injection.module

import com.zhongjiang.kotlin.splash.ui.activity.SplashActivity
import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class SplashActivityModule {
    @Provides
    @ActivityScope
    fun provideSplashActivity(splashActivity: SplashActivity):SplashActivity{
        return splashActivity
    }

}