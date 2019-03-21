package com.zhongjiang.kotlin.splash.presenter.splashfragment

import com.zhongjiang.kotlin.base.data.db.SplashAdEntity
import com.zhongjiang.kotlin.base.presenter.IModel
import com.zhongjiang.kotlin.base.presenter.IPresenter
import com.zhongjiang.kotlin.base.presenter.IView
import io.reactivex.Maybe
import io.rx_cache2.Reply

class SplashFragmentContract {
    interface Presenter : IPresenter {
        fun requestAdInfo(name: String)
        fun checkPermissions():Boolean
        fun checkSkip()
    }

    interface Model : IModel {
        fun requestAdInfo(name: String,update: Boolean): Maybe<Reply<List<SplashAdEntity>>>
    }

    interface View : IView {
        fun onShowAd(adBean: SplashAdEntity)
        fun onRefreshTimer(userInfo: String)
        fun onLoginSuccess()
        fun skipLogin()
        fun skipWeb(webUrl:String)
    }

}