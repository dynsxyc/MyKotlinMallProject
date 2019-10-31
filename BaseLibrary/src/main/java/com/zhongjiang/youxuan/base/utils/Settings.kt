package com.zhongjiang.youxuan.base.utils

import com.zhongjiang.youxuan.base.common.BaseApplication.AppContext

/**
 * @author dyn
 * @date on 2019/10/24  18:12
 * @packagename com.zhongjiang.youxuan.base.utils
 * @fileName Settings
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
object Settings {
    var test:String by Preference(AppContext,"","")
}