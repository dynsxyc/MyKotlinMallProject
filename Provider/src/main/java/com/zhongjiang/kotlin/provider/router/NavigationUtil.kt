package com.zhongjiang.kotlin.provider.router

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.zhongjiang.kotlin.base.busevent.ActivityRequestCode
import com.zhongjiang.kotlin.provider.R
import com.zhongjiang.kotlin.provider.router.NavigationConstant.Companion.NAVIGATION_DATA_BOOLEAN
import com.zhongjiang.kotlin.provider.router.NavigationConstant.Companion.NAVIGATION_DATA_WEBURL
import javax.inject.Inject

class NavigationUtil @Inject constructor() {
    companion object {

        fun navigationToMain() {
            ARouter.getInstance().build(RouterPath.MainCenter.PATH_MAIN).navigation()
        }

        fun navigationToLogin(isToLogin: Boolean) {
            ARouter.getInstance().build(RouterPath.SplashCenter.PATH_SPLASH_LOGIN).withBoolean(NAVIGATION_DATA_BOOLEAN, isToLogin).navigation()
        }
        /**
         * 跳转到web 有返回结果
         * */
        fun navigationToWebShowResult(context: Activity, webUrl: String) {
            navigationResult(RouterPath.BaseUI.PATH_WEBSHOW, ActivityRequestCode.REQUEST_WEBSHOW_CODE.requestCode,context, mapOf(Pair(NAVIGATION_DATA_WEBURL,webUrl)),null)
        }
        /**
         * 跳转到web 没有返回结果
         * */
        fun navigationToWebShow(webUrl: String) {
            navigation(RouterPath.BaseUI.PATH_WEBSHOW, mapOf(Pair(NAVIGATION_DATA_WEBURL,webUrl)),null)
        }
        private fun navigation(path:String, stringWidth: Map<String,String>?, booleanWidth:Map<String,Boolean>?){
            val builder = ARouter.getInstance().build(path)
                    stringWidth?.forEach{
                        builder.withString(it.key,it.value)
                    }
            booleanWidth?.forEach {
                builder.withBoolean(it.key,it.value)
            }
            builder.withTransition(R.anim.in_from_right, R.anim.out_to_left)
            builder.navigation()
        }
        private fun navigationResult(path:String,requestCode:Int,context: Activity, stringWidth: Map<String,String>?, booleanWidth:Map<String,Boolean>?){
            val builder = ARouter.getInstance().build(path)
                    stringWidth?.forEach{
                        builder.withString(it.key,it.value)
                    }
            booleanWidth?.forEach {
                builder.withBoolean(it.key,it.value)
            }
            builder.withTransition(R.anim.in_from_right, R.anim.in_from_left)
            builder.navigation(context,requestCode)
        }
    }
}