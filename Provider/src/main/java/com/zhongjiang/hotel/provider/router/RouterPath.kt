package com.zhongjiang.hotel.provider.router

object RouterPath {
    /**
    * SplashModule 模块
    * */
    object SplashCenter{
            const val PATH_SPLASH_LOGIN = "/splashCenter/login"
    }
    /**
     * App模块
     * */
    object MainCenter{
            const val PATH_MAIN = "/main/home"
    }
    /**
     * MainModule模块
     * */
    object MainModuleCenter{
            /**
             * 模块入口
             * */
            const val PATH_MAIN_MODULE_ENTRANCE = "/mainModule/entrance"
    }
    /**
     * MainModule模块
     * */
    object WebCenter{
            /**
             * 模块入口
             * */
            const val PATH_WEBSHOW = "/webModule/webActivity"
    }
}