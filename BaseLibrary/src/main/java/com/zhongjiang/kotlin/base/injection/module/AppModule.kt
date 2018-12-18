package com.zhongjiang.kotlin.base.injection.module

import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.injection.module.sheduler.AppSchedulerProvider
import com.zhongjiang.kotlin.base.injection.module.sheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
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
}
