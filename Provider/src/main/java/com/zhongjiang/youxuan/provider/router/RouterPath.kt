package com.zhongjiang.youxuan.provider.router

object RouterPath {
    /**
    * SplashModule 模块
    * */
    class SplashCenter{
        companion object {
            const val PATH_SPLASH_LOGIN = "/splashCenter/login"
        }
    }
    /**
     * App模块
     * */
    class MainCenter{
        companion object {
            const val PATH_MAIN = "/main/home"
        }
    }
    /**
     * MainModule模块
     * */
    class MainModuleCenter{
        companion object {
            /**
             * 模块入口
             * */
            const val PATH_MAIN_MODULE_ENTRANCE = "/mainModule/entrance"
        }
    }
    /**
     * BaseLibrary 模块中对应的 公共UI部分，因BaseLibrary 和 Provider的继承关系，无法直接使用这里的变量 需要保证值相同
     * */
    class BaseUI{
        companion object {
            const val PATH_WEBSHOW = "/baseui/webshow"
        }
    }
}