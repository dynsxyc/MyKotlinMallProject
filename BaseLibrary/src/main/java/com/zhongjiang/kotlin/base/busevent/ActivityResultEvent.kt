package com.zhongjiang.kotlin.base.busevent

import android.content.Intent

class ActivityResultEvent constructor(val requestCoder : Int, val resultCode : Int, val datar :Intent?) {
    companion object {
        enum class ActivityRequestCode constructor(val requestCode:Int){
            REQUEST_WEBSHOW_CODE(1)
        }
    }
}