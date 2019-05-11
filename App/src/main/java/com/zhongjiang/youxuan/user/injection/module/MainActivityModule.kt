package com.zhongjiang.youxuan.user.injection.module

import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.user.ui.activity.MainActivity
import com.zhongjiang.youxuan.user.ui.activity.TestStatusBarActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    @ActivityScope
    fun provideMainActivity(mainActivity: MainActivity):MainActivity{
        return mainActivity
    }
    @Provides
    @ActivityScope
    fun provideTestStatusBarActivity(mainActivity: TestStatusBarActivity):TestStatusBarActivity{
        return mainActivity
    }

}