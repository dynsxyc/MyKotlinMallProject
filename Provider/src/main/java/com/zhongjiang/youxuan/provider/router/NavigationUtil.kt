package com.zhongjiang.youxuan.provider.router

import android.app.Activity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.zhongjiang.youxuan.base.busevent.ActivityRequestCode
import com.zhongjiang.youxuan.provider.R
import com.zhongjiang.youxuan.provider.router.NavigationConstant.Companion.NAVIGATION_DATA_BOOLEAN
import com.zhongjiang.youxuan.provider.router.NavigationConstant.Companion.NAVIGATION_DATA_STRING_MODULE_MAP
import com.zhongjiang.youxuan.provider.router.NavigationConstant.Companion.NAVIGATION_DATA_STRING_MODULE_TYPE
import com.zhongjiang.youxuan.provider.router.NavigationConstant.Companion.NAVIGATION_DATA_WEBURL
import javax.inject.Inject

class NavigationUtil @Inject constructor() {
    companion object {
        /**
         * app主模块入口
         * */
        fun navigationToMain() {
            navigationToPath(RouterPath.MainCenter.PATH_MAIN)
        }

        /**
         * app主模块入口
         * */
        fun navigationToLogin(isToLogin: Boolean) {
            var builder = ARouter.getInstance().build(RouterPath.SplashCenter.PATH_SPLASH_LOGIN)
            builder.withBoolean(NAVIGATION_DATA_BOOLEAN,isToLogin)
            navigationResult(builder,null,-1)
        }

        /**
         * 跳转MainModule 主入口
         * */
        fun navigationToMainModuleEntrance(typeStr: String?, mapParams: HashMap<String,String>?) {
            navigationToModule(RouterPath.MainModuleCenter.PATH_MAIN_MODULE_ENTRANCE,typeStr,mapParams)
        }

        /**
         * 跳转到web 有返回结果
         * */
        fun navigationToWebShowResult(context: Activity, webUrl: String) {
            var builder = ARouter.getInstance().build(RouterPath.BaseUI.PATH_WEBSHOW)
            builder.withString(NAVIGATION_DATA_WEBURL,webUrl)
            navigationResult(builder,context,ActivityRequestCode.REQUEST_WEBSHOW_CODE.requestCode)
        }

        /**
         * 跳转到web 没有返回结果
         * */
        fun navigationToWebShow(webUrl: String) {
            var builder = ARouter.getInstance().build(RouterPath.BaseUI.PATH_WEBSHOW)
            builder.withString(NAVIGATION_DATA_WEBURL,webUrl)
            navigationResult(builder,null,-1)
        }

        /**
         * 单独path跳转
         * */
        private fun navigationToPath(path: String) {
            var builder = ARouter.getInstance().build(path)
            navigationResult(builder, null, -1)
        }
        /**
         * 模块间跳转
         * */
        private fun navigationToModule(path: String, typeStr: String?,mapParams: HashMap<String, String>?) {
            var builder = ARouter.getInstance().build(path)
            builder.withString(NAVIGATION_DATA_STRING_MODULE_TYPE,typeStr)
            builder.withSerializable(NAVIGATION_DATA_STRING_MODULE_MAP,mapParams)
            navigationResult(builder, null, -1)
        }

//        private fun navigation(path: String,
//                               context: Activity?,
//                               requestCode: Int,
//                               stringWidth: Map<String, String>?,
//                               booleanWidth: Map<String, Boolean>?,
//                               flags:ArrayList<Int>?,
//                               serializableWith:Map<String,Serializable>?,
//                               parcelableWith:Map<String, Parcelable>?,
//                               parcelableArrayListWith:Map<String, ArrayList<Parcelable>>?,
//                               integerArrayListWith:Map<String, ArrayList<Int>>?,
//                               stringArrayListWith:Map<String, ArrayList<String>>?) {
//            val builder = ARouter.getInstance().build(path)
//            stringWidth?.forEach {
//                builder.withString(it.key, it.value)
//            }
//            booleanWidth?.forEach {
//                builder.withBoolean(it.key, it.value)
//            }
//            flags?.forEach {
//                builder.addFlags(it)
//            }
//            serializableWith?.forEach {
//                builder.withSerializable(it.key,it.value)
//            }
//            parcelableWith?.forEach {
//                builder.withParcelable(it.key,it.value)
//            }
//            integerArrayListWith?.forEach {
//                builder.withIntegerArrayList(it.key,it.value)
//            }
//            stringArrayListWith?.forEach {
//                builder.withStringArrayList(it.key,it.value)
//            }
//            parcelableArrayListWith?.forEach {
//                builder.withParcelableArrayList(it.key,it.value)
//            }
//            builder.withTransition(R.anim.in_from_right, R.anim.out_to_left)
//            if (requestCode == -1){
//                builder.navigation()
//            }else{
//                builder.navigation(context,requestCode)
//            }
//        }
        /**
         * arouter 最终调用
         * */
        private fun navigationResult(builder: Postcard, context: Activity?, requestCode: Int) {
            builder.withTransition(R.anim.in_from_right, R.anim.in_from_left)
            if (context == null || requestCode == -1) {
                builder.navigation()
            } else {
                builder.navigation(context, requestCode)

            }

        }
    }
}