package com.zhongjiang.youxuan.user.presenter.mainactivity

import com.zhongjiang.youxuan.base.presenter.BaseModel
import com.zhongjiang.youxuan.user.presenter.mainfragment.MainFragmentContract
import com.zhongjiang.youxuan.user.splash.service.MainServiceManager
import javax.inject.Inject

class MainActivityModel @Inject constructor(manager: MainServiceManager) : BaseModel<MainServiceManager>(manager), MainFragmentContract.Model {
}