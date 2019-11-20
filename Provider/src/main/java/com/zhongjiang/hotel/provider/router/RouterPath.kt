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
     * BaseLibrary 模块中对应的 公共UI部分，因BaseLibrary 和 Provider的继承关系，无法直接使用这里的变量 需要保证值相同
     * */
    object BaseUI{
            const val PATH_WEBSHOW = "/baseui/webshow"
    }
}