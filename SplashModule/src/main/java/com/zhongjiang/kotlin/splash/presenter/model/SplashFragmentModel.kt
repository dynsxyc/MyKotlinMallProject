package com.zhongjiang.kotlin.splash.presenter.model

import com.zhongjiang.kotlin.base.data.net.entity.UserInfo
import com.zhongjiang.kotlin.base.data.net.service.GlobalServiceManager
import com.zhongjiang.kotlin.base.presenter.BaseModel
import com.zhongjiang.kotlin.splash.presenter.contract.SplashContract
import io.reactivex.Maybe
import javax.inject.Inject

class SplashFragmentModel @Inject constructor(serviceManager: GlobalServiceManager) : BaseModel<GlobalServiceManager>(serviceManager),SplashContract.Model {
    override fun requestUserInfo(name: String): Maybe<UserInfo> {
        return globalServiceManager.userInfoService.getUserInfo(name)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui());
    }
}