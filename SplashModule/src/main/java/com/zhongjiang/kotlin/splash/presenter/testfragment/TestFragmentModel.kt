package com.zhongjiang.kotlin.splash.presenter.loginfragment

import com.zhongjiang.kotlin.base.presenter.BaseModel
import com.zhongjiang.kotlin.splash.service.SplashServiceManager
import javax.inject.Inject

class TestFragmentModel @Inject constructor(manager: SplashServiceManager) : BaseModel<SplashServiceManager>(manager), TestFragmentContract.Model {
}