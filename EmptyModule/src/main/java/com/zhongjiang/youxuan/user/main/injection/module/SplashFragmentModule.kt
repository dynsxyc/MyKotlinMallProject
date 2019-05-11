package com.zhongjiang.kotlin.splash.injection.module

import com.zhongjiang.kotlin.splash.presenter.splashfragment.SplashFragmentContract
import com.zhongjiang.kotlin.splash.presenter.splashfragment.SplashFragmentModel
import com.zhongjiang.kotlin.splash.ui.fragment.SplashFragment
import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
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