package com.zhongjiang.hotel.provider.router

class NavigationConstant {
    companion object {
        /**
         * 单个Boolean值传递
         * */
        const val NAVIGATION_DATA_BOOLEAN = "boolean"
        /**
         * 网址url
         * */
        const val NAVIGATION_DATA_WEBURL = "webUrl"
        /**
         * 单模块跳转类型传递
         * */
        const val NAVIGATION_DATA_STRING_MODULE_TYPE = "stringModuleType"
        /**
         * 单模块跳转 参数以Map<string,string>传递
         * */
        const val NAVIGATION_DATA_STRING_MODULE_MAP = "stringModuleMap"
    }
}