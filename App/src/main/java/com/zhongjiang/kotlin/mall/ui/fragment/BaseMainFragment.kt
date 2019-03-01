package com.zhongjiang.kotlin.mall.ui.fragment

import com.zhongjiang.kotlin.base.presenter.IPresenter
import com.zhongjiang.kotlin.base.ui.fragment.BaseMvpFragment

abstract class BaseMainFragment<T:IPresenter> :BaseMvpFragment<T>(){
    override fun getSwipeBackEnable(): Boolean {
        return false
    }

//    override fun onBackPressedSupport(): Boolean {
//        return true
//    }
}