package com.zhongjiang.kotlin.splash.presenter.model

import com.zhongjiang.kotlin.base.ext.convertList
import com.zhongjiang.kotlin.base.ext.handlerThread
import com.zhongjiang.kotlin.base.presenter.BaseModel
import com.zhongjiang.kotlin.base.data.db.SplashAdBean
import com.zhongjiang.kotlin.splash.presenter.contract.SplashContract
import com.zhongjiang.kotlin.splash.service.SplashServiceManager
import com.zhongjiang.kotlin.user.data.protocol.SplashAdReq
import io.reactivex.Maybe
import javax.inject.Inject

class SplashFragmentModel @Inject constructor(manager: SplashServiceManager) : BaseModel<SplashServiceManager>(manager),SplashContract.Model {


    override fun requestAdInfo(name: String): Maybe<List<SplashAdBean>> {
        return serviceManager.splashService.LoadAd(SplashAdReq("1","1","")).convertList().handlerThread(schedulers)
    }

//    fun getUserInfoCache(dynamicKey: String, refresh: Boolean): MaybeTransformer<UserInfo, UserInfo> {
//        return {
//            maybe --> cacheProviders.userInfoCacheProviders
//                    .getUserInfo(maybe, DynamicKey(dynamicKey), EvictDynamicKey(refresh))
//        }

//    }
}