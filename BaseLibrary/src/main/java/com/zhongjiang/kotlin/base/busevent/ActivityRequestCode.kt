package com.zhongjiang.kotlin.base.busevent

/**应用中页面请求Code*/
enum class ActivityRequestCode constructor(var requestCode: Int) {
    REQUEST_WEBSHOW_CODE(111),
    REQUEST_IMAGESELECTOR_CODE(112)
}