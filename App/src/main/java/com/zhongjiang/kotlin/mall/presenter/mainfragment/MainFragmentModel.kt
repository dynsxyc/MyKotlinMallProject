package com.zhongjiang.kotlin.mall.presenter.mainfragment

import com.zhongjiang.kotlin.base.presenter.BaseModel
import com.zhongjiang.kotlin.mall.presenter.mainfragment.MainFragmentContract
import com.zhongjiang.kotlin.splash.service.MainServiceManager
import javax.inject.Inject

class MainFragmentModel @Inject constructor(manager: MainServiceManager) : BaseModel<MainServiceManager>(manager), MainFragmentContract.Model {
}
