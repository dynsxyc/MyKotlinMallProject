package com.zhongjiang.youxuan.user.injection.module

import com.zhongjiang.youxuan.user.data.api.MainService
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