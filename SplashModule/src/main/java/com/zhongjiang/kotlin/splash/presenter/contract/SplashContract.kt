package com.zhongjiang.kotlin.splash.presenter.contract

import com.zhongjiang.kotlin.base.presenter.IModel
import com.zhongjiang.kotlin.base.presenter.IPresenter
import com.zhongjiang.kotlin.base.presenter.IView
import com.zhongjiang.kotlin.splash.data.SplashAdBean
import io.reactivex.Maybe

class SplashContract {
    interface Presenter : IPresenter {
        fun requestUserInfo(name: String)
    }

    interface View : IView {
        fun onGetUserInfo(userInfo: String)
    }

    interface Model : IModel {
        fun requestUserInfo(name: String):Maybe<List<SplashAdBean>>
    }

}