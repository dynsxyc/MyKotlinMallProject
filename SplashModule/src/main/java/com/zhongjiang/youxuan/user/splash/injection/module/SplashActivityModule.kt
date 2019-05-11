package com.zhongjiang.youxuan.user.splash.injection.module

import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.user.splash.ui.activity.SplashActivity
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