package com.zhongjiang.youxuan.user.splash.injection.module

import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.user.splash.presenter.splashfragment.SplashFragmentContract
import com.zhongjiang.youxuan.user.splash.presenter.splashfragment.SplashFragmentModel
import com.zhongjiang.youxuan.user.splash.ui.fragment.SplashFragment
import dagger.Module
import dagger.Provides

@Module
class SplashFragmentModule {
    @Provides
    @ActivityScope
    fun provideSplashView(splashFragment: SplashFragment): SplashFragmentContract.View {
        return splashFragment
    }

    @Provides
    @ActivityScope
    fun provideSplashModel(splashFragmentModel: SplashFragmentModel): SplashFragmentContract.Model {
        return splashFragmentModel
    }

}