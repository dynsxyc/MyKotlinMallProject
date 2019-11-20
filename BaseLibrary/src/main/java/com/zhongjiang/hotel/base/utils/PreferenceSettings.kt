package com.zhongjiang.hotel.base.utils

import com.zhongjiang.hotel.base.common.BaseApplication.AppContext


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
}