package com.zhongjiang.kotlin.splash.presenter.loginfragment

import com.zhongjiang.kotlin.base.data.db.UserInfoEntity
import com.zhongjiang.kotlin.base.ext.convert
import com.zhongjiang.kotlin.base.ext.handlerThread
import com.zhongjiang.kotlin.base.presenter.BaseModel
import com.zhongjiang.kotlin.splash.data.VerificationCodeResuleInfo
import com.zhongjiang.kotlin.splash.service.SplashServiceManager
import io.reactivex.Maybe
import javax.inject.Inject

class LoginFragmentModel @Inject constructor(manager: SplashServiceManager) : BaseModel<SplashServiceManager>(manager), LoginFragmentContract.Model {
    override fun requestLogin(code:String,phoneStr: String, verificationCode: String): Maybe<UserInfoEntity> {
        return serviceManager.splashService.userLogin(code,verificationCode,phoneStr).convert().handlerThread(schedulers)
    }

    override fun requestVerificationCode(phoneStr: String): Maybe<VerificationCodeResuleInfo> {
        return serviceManager.splashService.loginGetVerificationCode(phoneStr).convert().handlerThread(schedulers)
    }
}