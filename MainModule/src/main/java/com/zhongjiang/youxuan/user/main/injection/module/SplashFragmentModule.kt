package com.zhongjiang.kotlin.splash.injection.module

import com.zhongjiang.kotlin.splash.presenter.splashfragment.TabMainFragmentContract
import com.zhongjiang.kotlin.splash.presenter.splashfragment.SplashFragmentModel
import com.zhongjiang.kotlin.splash.ui.fragment.TabMainFragment
import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class SplashFragmentModule {
    @Provides
    @ActivityScope
    fun provideSplashView(splashFragment: TabMainFragment): TabMainFragmentContract.View {
        return splashFragment
    }

    @Provides
    @ActivityScope
    fun provideSplashModel(splashFragmentModel: SplashFragmentModel): TabMainFragmentContract.Model {
        return splashFragmentModel
    }

}