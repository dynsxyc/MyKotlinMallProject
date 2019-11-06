package com.zhongjiang.youxuan.user.splash.ui.fragment.splash

import com.zhongjiang.youxuan.base.data.db.SplashAdEntity

class SplashFragmentContract {
    interface Presenter  {
        fun requestAdInfo(name: String)
        fun checkPermissions():Boolean
        fun checkSkip()
    }


    interface View {
        fun onShowAd(adBean: SplashAdEntity)
        fun onRefreshTimer(userInfo: String)
        fun onLoginSuccess()
        fun skipLogin()
        fun skipWeb(webUrl:String)
    }

}