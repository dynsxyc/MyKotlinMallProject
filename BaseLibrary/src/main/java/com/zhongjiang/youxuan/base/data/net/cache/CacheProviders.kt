package com.zhongjiang.youxuan.base.data.net.cache

import io.rx_cache2.internal.RxCache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheProviders @Inject
constructor(rxCache: RxCache) {
    val userInfoCacheProviders:UserInfoCacheProviders = rxCache.using(UserInfoCacheProviders::class.java)
}
