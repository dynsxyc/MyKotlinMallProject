package com.zhongjiang.kotlin.splash.injection.module

import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.mall.presenter.contract.MainActivityContract
import com.zhongjiang.kotlin.mall.presenter.model.MainActivityModel
import com.zhongjiang.kotlin.mall.ui.activity.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    @ActivityScope
    fun provideMainActivity(mainActivity: MainActivity):MainActivityContract.View{
        return mainActivity
    }
    @Provides
    @ActivityScope
    fun provideMainActivityModel(mainActivityModel: MainActivityModel):MainActivityContract.Model{
        return mainActivityModel
    }

}