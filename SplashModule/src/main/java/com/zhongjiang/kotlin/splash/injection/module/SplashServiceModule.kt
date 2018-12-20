package com.zhongjiang.kotlin.splash.injection.module

import com.zhongjiang.kotlin.user.data.api.SplashService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class SplashServiceModule {
    @Provides
    @Singleton
    fun provideSplashService(retrofit: Retrofit): SplashService {
        return retrofit.create(SplashService::class.java)
    }
}