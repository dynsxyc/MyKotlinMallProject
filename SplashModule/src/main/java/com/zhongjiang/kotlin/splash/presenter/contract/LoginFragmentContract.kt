package com.zhongjiang.kotlin.splash.presenter.contract

import com.zhongjiang.kotlin.base.presenter.IModel
import com.zhongjiang.kotlin.base.presenter.IPresenter
import com.zhongjiang.kotlin.base.presenter.IView

class LoginFragmentContract {
    interface Presenter : IPresenter {
    }

    interface Model : IModel {
        fun requestLogin()
    }

    interface View : IView {

    }
}