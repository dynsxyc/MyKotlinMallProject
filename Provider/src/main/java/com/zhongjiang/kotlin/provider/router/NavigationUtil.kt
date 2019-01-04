package com.zhongjiang.kotlin.provider.router

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.zhongjiang.kotlin.provider.router.NavigationConstant.Companion.NAVIGATION_DATA_BOOLEAN
import com.zhongjiang.kotlin.provider.router.NavigationConstant.Companion.NAVIGATION_DATA_WEBURL
import javax.inject.Inject

class NavigationUtil @Inject constructor() {
    companion object {

        fun navigationToMain() {
            ARouter.getInstance().build(RouterPath.MainCenter.PATH_MAIN).navigation();
        }

        fun navigationToLogin(isToLogin: Boolean) {
            ARouter.getInstance().build(RouterPath.SplashCenter.PATH_SPLASH_LOGIN).withBoolean(NAVIGATION_DATA_BOOLEAN, isToLogin).navigation()
        }

        fun navigationToWebShow(context: Activity,requestCode:Int,isSplash: Boolean, webUrl: String) {
            ARouter.getInstance().build(RouterPath.BaseUI.PATH_WEBSHOW).withBoolean(NAVIGATION_DATA_BOOLEAN, isSplash).withString(NAVIGATION_DATA_WEBURL, webUrl).navigation(context,requestCode)
        }
    }
}