package com.zhongjiang.kotlin.user.injection.module

import com.zhongjiang.kotlin.splash.service.SplashService
import com.zhongjiang.kotlin.splash.service.impl.SplashServiceIml
import dagger.Module
import dagger.Provides

/**
 * Created by dyn on 2018/7/16.
 */
@Module
class SplashModule {
    @Provides
    fun providesSplashService(service: SplashServiceIml):SplashService{
        return service
    }

}