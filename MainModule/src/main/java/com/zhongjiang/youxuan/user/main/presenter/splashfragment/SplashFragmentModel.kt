package com.zhongjiang.kotlin.splash.presenter.splashfragment

import com.zhongjiang.kotlin.splash.service.SplashServiceManager
import com.zhongjiang.youxuan.base.presenter.BaseModel
import javax.inject.Inject

class SplashFragmentModel @Inject constructor(manager: SplashServiceManager) : BaseModel<SplashServiceManager>(manager), TabMainFragmentContract.Model {
//    @Inject
//    lateinit var splashModuleRxCacheProviders: SplashModuleRxCacheProviders;
//    override fun requestAdInfo(name: String,update: Boolean): Maybe<Reply<List<SplashAdEntity>>> {
//        return splashModuleRxCacheProviders.getAdInfo(serviceManager.splashService.loadAd(SplashAdReq("1", "1", "")).convertList().handlerThread(schedulers), EvictProvider(update))
//    }

}