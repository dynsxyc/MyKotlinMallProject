package com.zhongjiang.kotlin.splash.presenter.splashfragment

import com.zhongjiang.kotlin.splash.ui.fragment.TabMainFragment
import com.zhongjiang.youxuan.base.ui.basemvp.BasePresenter
import com.zhongjiang.youxuan.base.utils.ULogger
import com.zhongjiang.hotel.provider.common.CommonUtils
import com.zhongjiang.youxuan.user.main.ui.MainModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by dyn on 2018/7/25.
 */
class TabMainFragmentPresenter @Inject constructor() : BasePresenter<TabMainFragment,MainModel>(), TabMainFragmentContract.Presenter {

    @Inject
    @Singleton
    lateinit var commonUtils: CommonUtils

    override fun onLocationCallback(isSuccess: Boolean) {
        ULogger.i("定位成功  callback $isSuccess")
    }


}