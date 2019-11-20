package com.zhongjiang.hotel.provider.event

import android.content.Intent

data class ActivityResultEvent constructor(val requestCoder : Int, val resultCode : Int, val intentData :Intent?)