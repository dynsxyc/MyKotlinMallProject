package com.zhongjiang.hotel.main.injection.module

import com.zhongjiang.hotel.main.service.api.MainService
import com.zhongjiang.hotel.main.data.cache.SplashModuleRxCacheProviders
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
    fun provideSplashModuleRxCacheProvider(rxCache: RxCache): SplashModuleRxCacheProviders {
        return rxCache.using(SplashModuleRxCacheProviders::class.java)
    }

}