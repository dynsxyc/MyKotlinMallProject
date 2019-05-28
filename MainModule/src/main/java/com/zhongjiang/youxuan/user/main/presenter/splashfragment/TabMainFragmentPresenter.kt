package com.zhongjiang.kotlin.splash.presenter.splashfragment

import com.orhanobut.logger.Logger
import com.zhongjiang.youxuan.base.presenter.BasePresenter
import com.zhongjiang.youxuan.provider.common.CommonUtils
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by dyn on 2018/7/25.
 */
class TabMainFragmentPresenter @Inject constructor(view: TabMainFragmentContract.View, model: TabMainFragmentContract.Model) : BasePresenter<TabMainFragmentContract.View, TabMainFragmentContract.Model>(view, model), TabMainFragmentContract.Presenter {

    @Inject
    @Singleton
    lateinit var commonUtils: CommonUtils

    override fun onLocationCallback(isSuccess: Boolean) {
        Logger.i("定位成功  callback $isSuccess")
    }


}