package com.zhongjiang.youxuan.user.splash.ui.fragment.login

import com.zhongjiang.youxuan.base.data.db.SplashAdEntity
import com.zhongjiang.youxuan.base.data.db.UserInfoEntity
import com.zhongjiang.youxuan.base.presenter.IModel
import com.zhongjiang.youxuan.base.presenter.IPresenter
import com.zhongjiang.youxuan.base.presenter.IView
import com.zhongjiang.youxuan.user.splash.data.VerificationCodeResuleInfo
import io.reactivex.Maybe
import io.rx_cache2.Reply

class LoginFragmentContract {
    interface Presenter  {
        fun requestVerificationCode(phoneStr: String)
        fun requestLogin(code:String,phoneStr: String,verificationCode: String)
    }

    interface View  {
        fun refreshVerificationCodeView(long:String)
        fun onLoginSuccess()
        fun timerFinish()
        fun getVerificationCodeSuccess(t:VerificationCodeResuleInfo)
    }
}