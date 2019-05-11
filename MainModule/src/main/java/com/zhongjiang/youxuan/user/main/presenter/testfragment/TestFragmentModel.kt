package com.zhongjiang.kotlin.splash.presenter.loginfragment

import com.zhongjiang.kotlin.splash.service.SplashServiceManager
import com.zhongjiang.youxuan.base.presenter.BaseModel
import javax.inject.Inject

class TestFragmentModel @Inject constructor(manager: SplashServiceManager) : BaseModel<SplashServiceManager>(manager), TestFragmentContract.Model {
}