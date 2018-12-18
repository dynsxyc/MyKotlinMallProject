package com.zhongjiang.kotlin.base.data.net.cache

import javax.inject.Inject
import javax.inject.Singleton

import io.rx_cache2.internal.RxCache

@Singleton
class CacheProviders @Inject
constructor(rxCache: RxCache) {

    val userInfoCacheProviders: UserInfoCacheProviders

    init {
        this.userInfoCacheProviders = rxCache.using(UserInfoCacheProviders::class.java)
    }
}
