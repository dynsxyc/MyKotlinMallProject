package com.zhongjiang.youxuan.user.main.common

import android.os.Parcel
import android.os.Parcelable

/**
 * @date on 2019/5/8 11:12
 * @packagename com.zhongjiang.youxuan.user.main.common
 * @author dyn
 * @fileName MainModuleSingleActivityEntity
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
data class MainModuleSingleActivityEntity(var type: MainModuleSingleActivityType, var stringWidth: HashMap<String, String>?, var booleanWidth: HashMap<String, Boolean>?) : Parcelable {
}