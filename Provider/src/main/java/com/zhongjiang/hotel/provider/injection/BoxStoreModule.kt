package com.zhongjiang.hotel.provider.injection

import com.zhongjiang.hotel.base.common.BaseApplication
import com.zhongjiang.hotel.provider.db.MyObjectBox
import com.zhongjiang.hotel.provider.db.SplashAdEntity
import com.zhongjiang.hotel.provider.db.UserInfoEntity
import dagger.Module
import dagger.Provides
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import javax.inject.Singleton

@Module
class BoxStoreModule {
    @Provides
    @Singleton
    fun providesBoxStore(application: BaseApplication): BoxStore {
        return MyObjectBox.builder().androidContext(application).build()
    }

    @Provides
    @Singleton
    fun providesBoxStoreForUserInfoEntity(boxStore: BoxStore): Box<UserInfoEntity> {
        return boxStore.boxFor(UserInfoEntity::class)
    }
    @Provides
    @Singleton
    fun providesBoxStoreForSplashAdEntity(boxStore: BoxStore): Box<SplashAdEntity> {
        return boxStore.boxFor(SplashAdEntity::class)
    }
}