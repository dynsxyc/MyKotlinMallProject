package com.zhongjiang.kotlin.splash.injection.module

import com.zhongjiang.kotlin.splash.presenter.loginfragment.LoginFragmentContract
import com.zhongjiang.kotlin.splash.presenter.loginfragment.LoginFragmentModel
import com.zhongjiang.kotlin.splash.presenter.loginfragment.TestFragmentContract
import com.zhongjiang.kotlin.splash.presenter.loginfragment.TestFragmentModel
import com.zhongjiang.kotlin.splash.ui.fragment.LoginFragment
import com.zhongjiang.kotlin.splash.ui.fragment.TestPictureFragment
import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class LoginFragmentModule {
    @Provides
    @ActivityScope
    fun provideLoginView(loginFragment: LoginFragment): LoginFragmentContract.View {
        return loginFragment
    }
    @Provides
    @ActivityScope
    fun provideTestView(loginFragment: TestPictureFragment): TestFragmentContract.View {
        return loginFragment
    }
    @Provides
    @ActivityScope
    fun provideTestModel(loginFragment: TestFragmentModel): TestFragmentContract.Model {
        return loginFragment
    }

    @Provides
    @ActivityScope
    fun provideLoginModel(loginFragmentModel: LoginFragmentModel): LoginFragmentContract.Model {
        return loginFragmentModel
    }

}