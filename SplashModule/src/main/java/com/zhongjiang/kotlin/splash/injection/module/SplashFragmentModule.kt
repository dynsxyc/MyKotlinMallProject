package com.zhongjiang.kotlin.splash.injection.module

import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.splash.presenter.contract.SplashFragmentContract
import com.zhongjiang.kotlin.splash.presenter.model.SplashFragmentModel
import com.zhongjiang.kotlin.splash.ui.fragment.SplashFragment
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