package com.zhongjiang.youxuan.base.injection.module

import dagger.Module
import dagger.Provides
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import java.io.File
import javax.inject.Singleton

/**
 * Created by QingMei on 2017/9/1.
 * desc:
 */
@Module
class CacheModule(private val cacheDir: File) {

    @Provides
    @Singleton
    fun provideRxCache(): RxCache {
        return RxCache.Builder()
                .persistence(cacheDir, GsonSpeaker())
    }

}
