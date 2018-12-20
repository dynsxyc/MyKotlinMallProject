package com.zhongjiang.kotlin.base.common

/**
 * Created by dyn on 2018/7/25.
 */
interface YouXuanNetInterfaceConstant {
    companion object {

        val BASE_URL_DEVELOP_TEST = "http://testapi.you-x.cn/";
        /** 默认测试环境 */
        val BASE_URL_DEVELOP_PRE = "http://preapi.you-x.cn/"
        val BASE_URL_DEVELOP_DEV = "http://devapi.you-x.cn/"
        /**
         * APP打开次数
         */
        const val API_METHOD_APP_INSERT_OPEN_NUMBER = "app/other/openApp/insertOpenNumber"
        /**
         * 启动页广告
         */
        const val API_METHOD_APP_APLASH_AD = "app/qt/appStartPage/queryList"
    }
}