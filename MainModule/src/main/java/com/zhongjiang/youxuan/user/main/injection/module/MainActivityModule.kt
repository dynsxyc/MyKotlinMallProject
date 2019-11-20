package com.zhongjiang.kotlin.splash.injection.module

import com.zhongjiang.hotel.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.user.main.ui.activity.MainSingleFragmentActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    @com.zhongjiang.hotel.base.injection.scope.ActivityScope
    fun provideSplashActivity(splashActivity: MainSingleFragmentActivity): MainSingleFragmentActivity {
        return splashActivity
    }

}