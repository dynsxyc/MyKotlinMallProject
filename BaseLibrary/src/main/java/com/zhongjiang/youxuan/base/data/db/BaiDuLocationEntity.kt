package com.zhongjiang.youxuan.base.data.db

import com.zhongjiang.youxuan.base.utils.BaiDuUtils.Companion.DEFAULT_ADDRESSSTR
import com.zhongjiang.youxuan.base.utils.BaiDuUtils.Companion.DEFAULT_CITY
import com.zhongjiang.youxuan.base.utils.BaiDuUtils.Companion.DEFAULT_DISTRICT
import com.zhongjiang.youxuan.base.utils.BaiDuUtils.Companion.DEFAULT_LATITUDE
import com.zhongjiang.youxuan.base.utils.BaiDuUtils.Companion.DEFAULT_LOCATION_ID
import com.zhongjiang.youxuan.base.utils.BaiDuUtils.Companion.DEFAULT_LONGITUDE
import com.zhongjiang.youxuan.base.utils.BaiDuUtils.Companion.DEFAULT_PROVINCE
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

/**
 * @date on 2019/5/28 9:13
 * @packagename com.zhongjiang.youxuan.base.data.db
 * @author dyn
 * @fileName BdLoactionEntity
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/

@Entity
class BaiDuLocationEntity constructor() {

    @Id(assignable = true)
    var id: Long = DEFAULT_LOCATION_ID
    /**
     * 是否包含有成功的定位信息
     */
    var hasSuccessLocation = 0
    /**
     * gps 获取当前位置的【纬度】
     */
    var latitude = DEFAULT_LATITUDE
    var latitudeStr = latitude.toString()
    /**
     * gps 获取当前位置的【经度】
     */
    var longitude = DEFAULT_LONGITUDE
    var longitudeStr = longitude.toString()
    /**
     * 获取当前位置的城市
     */
    var city = DEFAULT_CITY
    /**
     * 获取当前位置的省
     */
    var province = DEFAULT_PROVINCE
    /**
     * 获取当前位置的区
     */
    var district = DEFAULT_DISTRICT
    /**
     * 获取当前位置的详细地址信息
     */
    var addressStr = DEFAULT_ADDRESSSTR



    constructor(isHasSuccessLocation: Int, latitude: Double, latitudeStr: String, longitude: Double, longitudeStr: String, city: String, province: String, district: String, addressStr: String):this() {
        this.hasSuccessLocation = isHasSuccessLocation
        this.latitude = latitude
        this.latitudeStr = latitudeStr
        this.longitude = longitude
        this.longitudeStr = longitudeStr
        this.city = city
        this.province = province
        this.district = district
        this.addressStr = addressStr
    }
    fun clone(baiduLocationEntity: BaiDuLocationEntity):BaiDuLocationEntity{
        this.hasSuccessLocation = baiduLocationEntity.hasSuccessLocation
        this.latitude = baiduLocationEntity.latitude
        this.latitudeStr = baiduLocationEntity.latitudeStr
        this.longitude = baiduLocationEntity.longitude
        this.longitudeStr = baiduLocationEntity.longitudeStr
        this.city = baiduLocationEntity.city
        this.province = baiduLocationEntity.province
        this.district = baiduLocationEntity.district
        this.addressStr = baiduLocationEntity.addressStr
        return this
    }
    override fun toString(): String {
        return "hasSuccessLocation = $hasSuccessLocation  addressStr = $addressStr latitudeStr = $latitudeStr longitudeStr = $longitudeStr"
    }
}