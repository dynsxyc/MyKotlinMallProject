package com.zhongjiang.hotel.base.rx.event

import android.content.Intent

data class ActivityResultEvent(val requestCode:Int, val resultCode:Int, val intentData:Intent)