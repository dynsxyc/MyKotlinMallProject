package com.zhongjiang.kotlin.provider.router

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.zhongjiang.kotlin.base.busevent.ActivityResultEvent
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

        fun navigationToWebShowResult(context: Activity, webUrl: String) {
            ARouter.getInstance().build(RouterPath.BaseUI.PATH_WEBSHOW).withString(NAVIGATION_DATA_WEBURL, webUrl).navigation(context, ActivityResultEvent.Companion.ActivityRequestCode.REQUEST_WEBSHOW_CODE.requestCode)
        }
        fun navigationToWebShow(webUrl: String) {
            ARouter.getInstance().build(RouterPath.BaseUI.PATH_WEBSHOW).withString(NAVIGATION_DATA_WEBURL,webUrl).navigation()
        }
    }
}