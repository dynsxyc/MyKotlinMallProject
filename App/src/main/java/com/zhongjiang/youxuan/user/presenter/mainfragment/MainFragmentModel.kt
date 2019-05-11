package com.zhongjiang.youxuan.user.presenter.mainfragment

import com.zhongjiang.youxuan.base.presenter.BaseModel
import com.zhongjiang.youxuan.user.splash.service.MainServiceManager
import javax.inject.Inject

class MainFragmentModel @Inject constructor(manager: MainServiceManager) : BaseModel<MainServiceManager>(manager), MainFragmentContract.Model {
}
