package com.zhongjiang.hotel.base.data

/**
 * @date on 2019/5/28 9:13
 * @packagename com.zhongjiang.youxuan.base.data.db
 * @author dyn
 * @fileName BdLoactionEntity
 * @org com.zhongjiang.youxuan
 * @describe 百度定位
 * @email 583454199@qq.com
 * @param isSuccessLocation 当前数据是否是定位成功的数据
 * @param latitude 纬度
 **/

data class BaiDuLocationEntity(var isSuccessLocation: Boolean = false, var latitude: Double = 30.317662
                               , var longitude: Double = 120.260282, var city: String = "杭州市", var province: String = "浙江省"
                               , var district: String = "江干区", var address: String = "浙江省杭州市江干区"
)