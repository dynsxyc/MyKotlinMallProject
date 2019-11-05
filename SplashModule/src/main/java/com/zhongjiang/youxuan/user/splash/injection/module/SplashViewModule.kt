package com.zhongjiang.youxuan.user.splash.injection.module

import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.user.splash.ui.SplashDataModel
import com.zhongjiang.youxuan.user.splash.ui.fragment.splash.SplashFragmentContract
import com.zhongjiang.youxuan.user.splash.ui.activity.splash.SplashActivity
import com.zhongjiang.youxuan.user.splash.ui.fragment.login.LoginFragment
import com.zhongjiang.youxuan.user.splash.ui.fragment.splash.SplashFragment
import com.zhongjiang.youxuan.user.splash.ui.fragment.testpicture.TestPictureFragment
import dagger.Module
import dagger.Provides

@Module
class SplashViewModule {

    @Provides
    @ActivityScope
    fun provideLoginView(loginFragment: LoginFragment): LoginFragment {
        return loginFragment
    }

    @Provides
    @ActivityScope
    fun provideTestView(loginFragment: TestPictureFragment): TestPictureFragment {
        return loginFragment
    }

    @Provides
    @ActivityScope
    fun provideSplashView(splashFragment: SplashFragment): SplashFragment {
        return splashFragment
    }

    @Provides
    @ActivityScope
    fun provideSplashActivity(splashActivity: SplashActivity): SplashActivity {
        return splashActivity
    }
}