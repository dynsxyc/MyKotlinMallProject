package com.zhongjiang.youxuan.user.splash.presenter.loginfragment

import com.zhongjiang.youxuan.base.presenter.BaseModel
import com.zhongjiang.youxuan.user.splash.service.SplashServiceManager
import javax.inject.Inject

class TestFragmentModel @Inject constructor(manager: SplashServiceManager) : BaseModel<SplashServiceManager>(manager), TestFragmentContract.Model {
}