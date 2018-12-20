package com.zhongjiang.kotlin.base.injection.module

import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.data.db.MyObjectBox
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
class BoxStoreModule {
    @Provides
    @Singleton
    fun providesBoxStore(application: BaseApplication):BoxStore{
        return MyObjectBox.builder().androidContext(application).build()
    }
}