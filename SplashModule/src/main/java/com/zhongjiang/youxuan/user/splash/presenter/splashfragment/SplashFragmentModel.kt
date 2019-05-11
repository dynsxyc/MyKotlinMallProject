package com.zhongjiang.youxuan.user.splash.presenter.splashfragment

import com.zhongjiang.youxuan.base.data.db.SplashAdEntity
import com.zhongjiang.youxuan.base.ext.convertList
import com.zhongjiang.youxuan.base.ext.handlerThread
import com.zhongjiang.youxuan.base.presenter.BaseModel
import com.zhongjiang.youxuan.user.splash.data.cache.SplashModuleRxCacheProviders
import com.zhongjiang.youxuan.user.splash.service.SplashServiceManager
import com.zhongjiang.youxuan.user.data.protocol.SplashAdReq
import io.reactivex.Maybe
import io.rx_cache2.EvictProvider
import io.rx_cache2.Reply
import javax.inject.Inject

class SplashFragmentModel @Inject constructor(manager: SplashServiceManager) : BaseModel<SplashServiceManager>(manager), SplashFragmentContract.Model {
    @Inject
    lateinit var splashModuleRxCacheProviders: SplashModuleRxCacheProviders;
    override fun requestAdInfo(name: String,update: Boolean): Maybe<Reply<List<SplashAdEntity>>> {
        return splashModuleRxCacheProviders.getAdInfo(serviceManager.splashService.loadAd(SplashAdReq("1", "1", "")).convertList().handlerThread(schedulers), EvictProvider(update))
    }

}