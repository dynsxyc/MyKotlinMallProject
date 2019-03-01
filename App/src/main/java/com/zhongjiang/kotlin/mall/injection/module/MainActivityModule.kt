package com.zhongjiang.kotlin.mall.injection.module

import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.mall.ui.activity.MainActivity
import com.zhongjiang.kotlin.mall.ui.activity.TestStatusBarActivity
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