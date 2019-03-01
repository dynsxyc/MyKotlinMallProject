package com.zhongjiang.kotlin.mall.presenter.mainactivity

import com.zhongjiang.kotlin.base.presenter.BaseModel
import com.zhongjiang.kotlin.mall.presenter.mainfragment.MainFragmentContract
import com.zhongjiang.kotlin.splash.service.MainServiceManager
import javax.inject.Inject

class MainActivityModel @Inject constructor(manager: MainServiceManager) : BaseModel<MainServiceManager>(manager), MainFragmentContract.Model {
}