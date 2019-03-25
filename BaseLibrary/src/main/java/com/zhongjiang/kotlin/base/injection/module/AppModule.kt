package com.zhongjiang.kotlin.base.injection.module

import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.injection.module.sheduler.AppSchedulerProvider
import com.zhongjiang.kotlin.base.injection.module.sheduler.SchedulerProvider
import com.zhongjiang.kotlin.base.oss.BucketType
import com.zhongjiang.kotlin.base.oss.OssService
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by QingMei on 2017/8/14.
 * desc:
 */
@Module
class AppModule(private val application: BaseApplication) {

    @Singleton
    @Provides
    fun provideApplication(): BaseApplication {
        return application
    }

    @Singleton
    @Provides
    fun provideSchedulers(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    @Named("public")
    fun providePublicOsservice(context: BaseApplication,schedulerProvider: SchedulerProvider):OssService{
        return OssService(context,schedulerProvider,BucketType.BUCKET_CONFIT_TAG_PUBLIC)
    }

    @Provides
    @Singleton
    @Named("security")
    fun provideSecurityOsservice(context: BaseApplication,schedulerProvider: SchedulerProvider):OssService{
        return OssService(context,schedulerProvider,BucketType.BUCKET_CONFIT_TAG_SECURITY)
    }
}
