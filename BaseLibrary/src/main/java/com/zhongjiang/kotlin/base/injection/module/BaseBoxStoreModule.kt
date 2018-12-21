package com.zhongjiang.kotlin.base.injection.module

import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.data.db.MyObjectBox
import com.zhongjiang.kotlin.base.data.db.SplashAdBean
import com.zhongjiang.kotlin.base.data.db.UserDb
import dagger.Module
import dagger.Provides
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
class BaseBoxStoreModule {
    @Provides
    @Singleton
    fun providesBoxStore(application: BaseApplication):BoxStore{
        return MyObjectBox.builder().androidContext(application).build()
    }

    @Provides
    @Singleton
    fun provideUserDbBox(boxStore: BoxStore): Box<UserDb> {
        return boxStore.boxFor(UserDb::class.java)
    }

    @Provides
    @Singleton
    fun providesSplashBox(boxStore: BoxStore): Box<SplashAdBean> {
        return boxStore.boxFor(SplashAdBean::class.java)
    }

}