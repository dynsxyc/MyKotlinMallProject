package com.zhongjiang.youxuan.user.splash.data.cache

import com.zhongjiang.youxuan.base.data.db.SplashAdEntity
import io.reactivex.Maybe
import io.rx_cache2.EncryptKey
import io.rx_cache2.EvictProvider
import io.rx_cache2.LifeCache
import io.rx_cache2.Reply
import java.util.concurrent.TimeUnit

@EncryptKey("123")
interface SplashModuleRxCacheProviders {
    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    fun getAdInfo(maybe: Maybe<List<SplashAdEntity>>,evictProvider: EvictProvider): Maybe<Reply<List<SplashAdEntity>>>
}