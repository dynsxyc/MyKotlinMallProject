package com.zhongjiang.kotlin.base.injection.module


import com.zhongjiang.kotlin.base.data.net.service.GlobalService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class GlobalServiceModule {

    @Singleton
    @Provides
    fun provideUserInfoService(retrofit: Retrofit): GlobalService {
        return retrofit.create(GlobalService::class.java)
    }
}
