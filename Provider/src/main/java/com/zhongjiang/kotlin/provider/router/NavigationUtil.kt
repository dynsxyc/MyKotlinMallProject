package com.zhongjiang.kotlin.provider.router

import com.alibaba.android.arouter.launcher.ARouter
import com.zhongjiang.kotlin.provider.router.NavigationConstant.Companion.NAVIGATION_DATA_BOOLEAN
import javax.inject.Inject

class NavigationUtil @Inject constructor() {
    fun navigationToMain(){
        ARouter.getInstance().build(RouterPath.MainCenter.PATH_MAIN).navigation();
    }
    fun navigationToLogin(isToLogin: Boolean){
        ARouter.getInstance().build(RouterPath.SplashCenter.PATH_SPLASH_LOGIN).withBoolean(NAVIGATION_DATA_BOOLEAN,isToLogin).navigation();
    }
}