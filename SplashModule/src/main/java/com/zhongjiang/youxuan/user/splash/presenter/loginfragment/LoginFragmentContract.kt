package com.zhongjiang.youxuan.user.splash.presenter.loginfragment

import com.zhongjiang.youxuan.base.data.db.UserInfoEntity
import com.zhongjiang.youxuan.base.presenter.IModel
import com.zhongjiang.youxuan.base.presenter.IPresenter
import com.zhongjiang.youxuan.base.presenter.IView
import com.zhongjiang.youxuan.user.splash.data.VerificationCodeResuleInfo
import io.reactivex.Maybe

class LoginFragmentContract {
    interface Presenter : IPresenter {
        fun requestVerificationCode(phoneStr: String)
        fun requestLogin(code:String,phoneStr: String,verificationCode: String)
    }

    interface Model : IModel {
        /**请求登录*/
        fun requestLogin(code:String,phoneStr:String,verificationCode:String):Maybe<UserInfoEntity>
        /**获取验证码*/
        fun requestVerificationCode(phoneStr:String):Maybe<VerificationCodeResuleInfo>
    }

    interface View : IView {
        fun refreshVerificationCodeView(long:String)
        fun onLoginSuccess()
        fun timerFinish()
        fun getVerificationCodeSuccess(t:VerificationCodeResuleInfo)
    }
}