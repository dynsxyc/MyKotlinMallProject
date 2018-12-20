package com.zhongjiang.kotlin.splash.data

/**
 * Created by SSD董 on 2017/3/30.
 */

data class SplashAdBean constructor(var id:Int,var isImgLoadSuccess: Boolean) {
    lateinit var imgTitle: String

    var localImgPath: String? = ""

    var imgPathUrl: String? = ""

    var imgPageUrl: String? = ""

    var pageStatus: Int = 0

    var appType: Int = 0

    var showTime: Int = 0

    var funcType: String? = ""

}
