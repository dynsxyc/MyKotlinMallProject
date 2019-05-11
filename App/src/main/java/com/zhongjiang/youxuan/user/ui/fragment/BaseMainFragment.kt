package com.zhongjiang.youxuan.user.ui.fragment

import com.zhongjiang.youxuan.base.presenter.IPresenter
import com.zhongjiang.youxuan.base.ui.fragment.BaseMvpFragment

abstract class BaseMainFragment<T:IPresenter> :BaseMvpFragment<T>(){
    override fun getSwipeBackEnable(): Boolean {
        return false
    }
}