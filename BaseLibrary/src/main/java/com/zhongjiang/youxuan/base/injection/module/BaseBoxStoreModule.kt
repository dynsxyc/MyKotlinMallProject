package com.zhongjiang.youxuan.base.injection.module

import com.zhongjiang.youxuan.base.common.BaseApplication
import com.zhongjiang.youxuan.base.data.db.*
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
    @Provides
    @Singleton
    fun providesLocationAreaBox(boxStore: BoxStore): Box<LocationAreaInfoEntity> {
        return boxStore.boxFor(LocationAreaInfoEntity::class.java)
    }
    @Provides
    @Singleton
    fun providesBaiDuLocationBox(boxStore: BoxStore): Box<BaiDuLocationEntity> {
        return boxStore.boxFor(BaiDuLocationEntity::class.java)
    }

}