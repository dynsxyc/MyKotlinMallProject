package com.zhongjiang.kotlin.splash.presenter.contract

import com.zhongjiang.kotlin.base.data.db.SplashAdBean
import com.zhongjiang.kotlin.base.presenter.IModel
import com.zhongjiang.kotlin.base.presenter.IPresenter
import com.zhongjiang.kotlin.base.presenter.IView
import io.reactivex.Maybe

class SplashContract {
    interface Presenter : IPresenter {
        fun requestAdInfo(name: String)
        fun checkPermissions():Boolean
    }

    interface Model : IModel {
        fun requestAdInfo(name: String): Maybe<List<SplashAdBean>>
    }

    interface View : IView {
        fun onShowAd(userInfo: String)

    }

}