package com.zhongjiang.kotlin.base.presenter

import com.zhongjiang.kotlin.base.presenter.view.BaseView

/**
 * Created by dyn on 2018/7/13.
 */
open class BasePresenter<T : BaseView> {
    lateinit var mView : T
}