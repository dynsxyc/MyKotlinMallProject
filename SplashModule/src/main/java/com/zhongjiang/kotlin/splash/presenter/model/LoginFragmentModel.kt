package com.zhongjiang.kotlin.splash.presenter.model

import com.zhongjiang.kotlin.base.presenter.BaseModel
import com.zhongjiang.kotlin.splash.presenter.contract.LoginFragmentContract
import com.zhongjiang.kotlin.splash.service.SplashServiceManager
import javax.inject.Inject

class LoginFragmentModel @Inject constructor(manager: SplashServiceManager) : BaseModel<SplashServiceManager>(manager), LoginFragmentContract.Model {

    override fun requestLogin() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


//    fun getUserInfoCache(dynamicKey: String, refresh: Boolean): MaybeTransformer<UserInfo, UserInfo> {
//        return {
//            maybe --> cacheProviders.userInfoCacheProviders
//                    .getUserInfo(maybe, DynamicKey(dynamicKey), EvictDynamicKey(refresh))
//        }

//    }
}