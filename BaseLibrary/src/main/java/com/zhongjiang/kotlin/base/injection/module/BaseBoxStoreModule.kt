package com.zhongjiang.kotlin.base.injection.module

import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.data.db.MyObjectBox
import com.zhongjiang.kotlin.base.data.db.SplashAdEntity
import com.zhongjiang.kotlin.base.data.db.UserInfoEntity
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
    fun provideUserInfoBox(boxStore: BoxStore): Box<UserInfoEntity> {
        return boxStore.boxFor(UserInfoEntity::class.java)
    }

    @Provides
    @Singleton
    fun providesSplashAdBox(boxStore: BoxStore): Box<SplashAdEntity> {
        return boxStore.boxFor(SplashAdEntity::class.java)
    }

}