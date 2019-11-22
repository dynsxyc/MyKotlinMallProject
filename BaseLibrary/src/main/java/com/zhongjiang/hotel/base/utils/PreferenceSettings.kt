package com.zhongjiang.hotel.base.utils

import com.google.gson.Gson
import com.zhongjiang.hotel.base.common.BaseApplication.AppContext
import com.zhongjiang.hotel.base.data.BaiDuLocationEntity


/**
 * @author dyn
 * @date on 2019/10/24  18:12
 * @packagename com.zhongjiang.youxuan.base.utils
 * @fileName PreferenceSettings
 * @org com.zhongjiang.youxuan
 * @describe SharedPreference 工具
 * @email 583454199@qq.com
 **/
object PreferenceSettings {
    var test:String by Preference(AppContext,"","")
    var mBaiDuLocation:String by Preference(AppContext,"BaiDuLocation",toJson(BaiDuLocationEntity()))

    fun <T> fromJson(value: String,classOfT :Class<T>): T {
        return Gson().fromJson(value, classOfT)
    }
    fun toJson(any:Any): String {
        return Gson().toJson(any)
    }
}