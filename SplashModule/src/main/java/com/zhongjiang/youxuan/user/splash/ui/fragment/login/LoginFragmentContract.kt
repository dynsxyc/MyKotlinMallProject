package com.zhongjiang.youxuan.user.splash.ui.fragment.login

import com.zhongjiang.youxuan.user.splash.data.VerificationCodeResuleInfo

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