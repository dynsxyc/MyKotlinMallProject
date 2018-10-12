package com.zhongjiang.kotlin.base.injection.module

import android.content.Context
import com.zhongjiang.kotlin.base.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by dyn on 2018/7/17.
 */
@Module
class AppModule(private val baseApplication: BaseApplication) {
    @Provides
    @Singleton
    fun providesContext():Context{
        return baseApplication
    }
}