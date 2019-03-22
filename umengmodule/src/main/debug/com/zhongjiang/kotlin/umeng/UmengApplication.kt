package com.zhongjiang.kotlin.umeng

import android.app.Application
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig

class UmengApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        UMConfigure.init(this,"58a5046cf29d987c4f0010b2"
                ,"umeng", UMConfigure.DEVICE_TYPE_PHONE,"")//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        PlatformConfig.setWeixin("wxad2b629c142f621b", "e7ec5cc9375f46219243a377a2d152be")
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("530604790", "28a6f74e01bfd8c63c62e1ff1c0ab34d","http://www.sina.com")
        PlatformConfig.setQQZone("1105421937", "iKIxxXwf57YZTjWV")
        PlatformConfig.setAlipay("2019032163591991")
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true)
    }
}