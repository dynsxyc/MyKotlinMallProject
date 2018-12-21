package com.zhongjiang.kotlin.splash.injection.module

import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.splash.presenter.contract.SplashContract
import com.zhongjiang.kotlin.splash.presenter.model.SplashFragmentModel
import com.zhongjiang.kotlin.splash.ui.fragment.SplashFragment
import dagger.Module
import dagger.Provides

@Module()
class SplashFragmentModule {
    @Provides
    @ActivityScope
    fun provideSplashView(splashFragment: SplashFragment): SplashContract.View {
        return splashFragment
    }

    @Provides
    @ActivityScope
    fun provideSplashModel(splashFragmentModel: SplashFragmentModel): SplashContract.Model {
        return splashFragmentModel
    }

}