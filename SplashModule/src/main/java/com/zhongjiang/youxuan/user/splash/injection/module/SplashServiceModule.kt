package com.zhongjiang.youxuan.user.splash.injection.module

import com.zhongjiang.youxuan.user.splash.data.cache.SplashModuleRxCacheProviders
import com.zhongjiang.youxuan.user.data.api.SplashService
import dagger.Module
import dagger.Provides
import io.rx_cache2.internal.RxCache
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class SplashServiceModule {
    @Provides
    @Singleton
    fun provideSplashService(retrofit: Retrofit): SplashService {
        return retrofit.create(SplashService::class.java)
    }

    @Provides
    @Singleton
    fun provideSplashModuleRxCacheProvider(rxCache: RxCache):SplashModuleRxCacheProviders{
        return rxCache.using(SplashModuleRxCacheProviders::class.java)
    }

}