package com.zhongjiang.kotlin.mall.presenter.model

import com.zhongjiang.kotlin.base.presenter.BaseModel
import com.zhongjiang.kotlin.mall.presenter.contract.MainActivityContract
import com.zhongjiang.kotlin.splash.service.MainServiceManager
import javax.inject.Inject

class MainActivityModel @Inject constructor(manager: MainServiceManager) : BaseModel<MainServiceManager>(manager), MainActivityContract.Model {
}