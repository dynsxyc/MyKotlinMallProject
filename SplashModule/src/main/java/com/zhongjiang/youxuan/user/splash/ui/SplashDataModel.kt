package com.zhongjiang.youxuan.user.splash.ui

import com.zhongjiang.youxuan.base.data.db.SplashAdEntity
import com.zhongjiang.youxuan.base.data.db.UserInfoEntity
import com.zhongjiang.youxuan.base.ext.convert
import com.zhongjiang.youxuan.base.ext.convertList
import com.zhongjiang.youxuan.base.ext.handlerThread
import com.zhongjiang.youxuan.base.ui.basemvp.BaseModel
import com.zhongjiang.youxuan.user.data.protocol.SplashAdReq
import com.zhongjiang.youxuan.user.splash.data.VerificationCodeResuleInfo
import com.zhongjiang.youxuan.user.splash.data.cache.SplashModuleRxCacheProviders
import com.zhongjiang.youxuan.user.splash.service.SplashServiceManager
import com.zhongjiang.youxuan.user.splash.ui.fragment.ISplashModel
import io.reactivex.Maybe
import io.rx_cache2.EvictProvider
import io.rx_cache2.Reply
import javax.inject.Inject

class SplashDataModel @Inject constructor(): BaseModel<SplashServiceManager>(), ISplashModel{
    override fun requestLogin(code:String,phoneStr: String, verificationCode: String): Maybe<UserInfoEntity> {
        return serviceManager.splashService.userLogin(code,verificationCode,phoneStr).convert().handlerThread(schedulers)
    }

    override fun requestVerificationCode(phoneStr: String): Maybe<VerificationCodeResuleInfo> {
        return serviceManager.splashService.loginGetVerificationCode(phoneStr).convert().handlerThread(schedulers)
    }

    @Inject
    lateinit var splashModuleRxCacheProviders: SplashModuleRxCacheProviders;
    override fun requestAdInfo(name: String,update: Boolean): Maybe<Reply<List<SplashAdEntity>>> {
        return splashModuleRxCacheProviders.getAdInfo(serviceManager.splashService.loadAd().convertList().handlerThread(schedulers), EvictProvider(update))
    }
}