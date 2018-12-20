package com.zhongjiang.kotlin.base.injection.module


import com.zhongjiang.kotlin.base.data.net.service.GlobalService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton
/**
 * 全局公用接口 在baseModel 中可以调用
 * */
@Module
class GlobalServiceModule {

    @Singleton
    @Provides
    fun provideGlobalService(retrofit: Retrofit): GlobalService {
        return retrofit.create(GlobalService::class.java)
    }
}
