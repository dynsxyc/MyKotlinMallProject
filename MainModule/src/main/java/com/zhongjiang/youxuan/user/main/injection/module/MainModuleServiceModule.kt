package com.zhongjiang.kotlin.splash.injection.module

import com.zhongjiang.kotlin.splash.data.cache.SplashModuleRxCacheProviders
import com.zhongjiang.kotlin.user.data.api.MainService
import dagger.Module
import dagger.Provides
import io.rx_cache2.internal.RxCache
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class MainModuleServiceModule {
    @Provides
    @Singleton
    fun provideSplashService(retrofit: Retrofit): MainService {
        return retrofit.create(MainService::class.java)
    }

    @Provides
    @Singleton
    fun provideSplashModuleRxCacheProvider(rxCache: RxCache):SplashModuleRxCacheProviders{
        return rxCache.using(SplashModuleRxCacheProviders::class.java)
    }

}