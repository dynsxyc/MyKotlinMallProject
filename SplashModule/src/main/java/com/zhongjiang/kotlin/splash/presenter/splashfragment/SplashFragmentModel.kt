package com.zhongjiang.kotlin.splash.presenter.splashfragment

import com.zhongjiang.kotlin.base.data.db.SplashAdEntity
import com.zhongjiang.kotlin.base.ext.convertList
import com.zhongjiang.kotlin.base.ext.handlerThread
import com.zhongjiang.kotlin.base.presenter.BaseModel
import com.zhongjiang.kotlin.splash.service.SplashServiceManager
import com.zhongjiang.kotlin.user.data.protocol.SplashAdReq
import io.reactivex.Maybe
import io.reactivex.functions.Function
import javax.inject.Inject

class SplashFragmentModel @Inject constructor(manager: SplashServiceManager) : BaseModel<SplashServiceManager>(manager), SplashFragmentContract.Model {

    override fun requestAdInfo(name: String): Maybe<SplashAdEntity> {
        return serviceManager.splashService.LoadAd(SplashAdReq("1", "1", "")).convertList().handlerThread(schedulers).flatMap(Function<List<SplashAdEntity>, Maybe<SplashAdEntity>> {
            if (it.isEmpty()) {
                return@Function Maybe.error(NullPointerException())
            } else {
                return@Function Maybe.just(it[0])
            }
        })
    }

//    fun getUserInfoCache(dynamicKey: String, refresh: Boolean): MaybeTransformer<UserInfo, UserInfo> {
//        return {
//            maybe --> cacheProviders.userInfoCacheProviders
//                    .getUserInfo(maybe, DynamicKey(dynamicKey), EvictDynamicKey(refresh))
//        }

//    }
}