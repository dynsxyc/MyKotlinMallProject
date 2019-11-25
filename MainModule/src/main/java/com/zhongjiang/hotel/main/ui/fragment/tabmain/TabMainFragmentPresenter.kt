package com.zhongjiang.hotel.main.ui.fragment.tabmain

import com.zhongjiang.hotel.base.ui.basemvp.BasePresenter
import com.zhongjiang.hotel.base.utils.ULogger
import com.zhongjiang.hotel.main.ui.MainModel
import com.zhongjiang.hotel.provider.common.CommonUtils
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by dyn on 2018/7/25.
 */
class TabMainFragmentPresenter @Inject constructor(mModule: MainModel) : BasePresenter<TabMainFragment>(mModule), TabMainFragmentContract.Presenter {

    @Inject
    @Singleton
    lateinit var commonUtils: CommonUtils

    override fun onLocationCallback(isSuccess: Boolean) {
        ULogger.i("定位成功  callback $isSuccess")
    }


}