package com.zhongjiang.kotlin.splash.presenter.splashfragment

import com.zhongjiang.kotlin.base.data.db.SplashAdEntity
import com.zhongjiang.kotlin.base.ext.convertList
import com.zhongjiang.kotlin.base.ext.handlerThread
import com.zhongjiang.kotlin.base.presenter.BaseModel
import com.zhongjiang.kotlin.splash.data.cache.SplashModuleRxCacheProviders
import com.zhongjiang.kotlin.splash.service.SplashServiceManager
import com.zhongjiang.kotlin.user.data.protocol.SplashAdReq
import io.reactivex.Maybe
import io.rx_cache2.EvictProvider
import io.rx_cache2.Reply
import javax.inject.Inject

class SplashFragmentModel @Inject constructor(manager: SplashServiceManager) : BaseModel<SplashServiceManager>(manager), SplashFragmentContract.Model {
    @Inject
    lateinit var splashModuleRxCacheProviders: SplashModuleRxCacheProviders;
    override fun requestAdInfo(name: String,update: Boolean): Maybe<Reply<List<SplashAdEntity>>> {
        return splashModuleRxCacheProviders.getAdInfo(serviceManager.splashService.LoadAd(SplashAdReq("1", "1", "")).convertList().handlerThread(schedulers), EvictProvider(update))
    }

}