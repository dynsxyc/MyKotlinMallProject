package com.zhongjiang.kotlin.base.injection.module

import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.base.ui.fragment.YXWebShowFragment
import dagger.Module
import dagger.Provides

@Module
class ProviderUIModule {
        @Provides
        @ActivityScope
        fun provideSplashView(webshowFragment: YXWebShowFragment): YXWebShowFragment {
            return webshowFragment
        }

}