package com.zhongjiang.kotlin.mall.injection.module

import com.zhongjiang.kotlin.user.data.api.MainService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class MainServiceModule {
    @Provides
    @Singleton
    fun provideMainService(retrofit: Retrofit): MainService {
        return retrofit.create(MainService::class.java)
    }

}