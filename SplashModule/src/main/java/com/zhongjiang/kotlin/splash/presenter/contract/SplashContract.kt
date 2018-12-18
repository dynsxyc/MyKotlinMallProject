package com.zhongjiang.kotlin.splash.presenter.contract

import com.zhongjiang.kotlin.base.data.net.entity.UserInfo
import com.zhongjiang.kotlin.base.presenter.IModel
import com.zhongjiang.kotlin.base.presenter.IPresenter
import com.zhongjiang.kotlin.base.presenter.IView
import io.reactivex.Maybe

class SplashContract {
    open interface Presenter : IPresenter {
        fun requestUserInfo(name: String)
    }

    open interface View : IView {
        fun onGetUserInfo(userInfo: UserInfo)
    }

    open interface Model : IModel {
        fun requestUserInfo(name: String):Maybe<UserInfo>
    }

}