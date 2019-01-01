package com.zhongjiang.kotlin.splash.injection.module

import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.splash.presenter.loginfragment.LoginFragmentContract
import com.zhongjiang.kotlin.splash.presenter.loginfragment.LoginFragmentModel
import com.zhongjiang.kotlin.splash.ui.fragment.LoginFragment
import dagger.Module
import dagger.Provides

@Module
class LoginFragmentModule {
    @Provides
    @ActivityScope
    fun provideLoginView(splashFragment: LoginFragment): LoginFragmentContract.View {
        return splashFragment
    }

    @Provides
    @ActivityScope
    fun provideLoginModel(splashFragmentModel: LoginFragmentModel): LoginFragmentContract.Model {
        return splashFragmentModel
    }

}