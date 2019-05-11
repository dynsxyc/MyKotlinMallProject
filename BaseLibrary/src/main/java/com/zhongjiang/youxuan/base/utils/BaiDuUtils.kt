package com.zhongjiang.youxuan.base.utils

import android.text.TextUtils
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import javax.inject.Inject

/**
 * @date on 2019/5/10 17:08
 * @packagename com.zhongjiang.youxuan.base.utils
 * @author dyn
 * @fileName BaiDuUtils
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
class BaiDuUtils @Inject constructor(){

    @Inject
    lateinit var mLocationClient: LocationClient
    companion object {
        /**
         * 是否包含有成功的定位信息
         */
        open var isHasSuccessLocation = false
        /**
         * gps 获取当前位置的【纬度】
         */
        open var latitude = 30.317662
        open var latitudeStr = latitude.toString()
        /**
         * gps 获取当前位置的【经度】
         */
        open var longitude = 120.260282
        open var longitudeStr = longitude.toString()
        /**
         * 获取当前位置的城市
         */
        open var city = "杭州市"
        /**
         * 获取当前位置的省
         */
        open var province = "浙江省"
        /**
         * 获取当前位置的区
         */
        open var district = "江干区"
        /**
         * 获取当前位置的详细地址信息
         */
        open var addrStr: String? = null

    }

    open fun start(mBaseLocationListenner : BaseLocationListener){
        this.mLocationClient.registerLocationListener(mBaseLocationListenner)
        this.mLocationClient.start()
    }
    private fun stop(mBaseLocationListenner : BaseLocationListener){
        mLocationClient.unRegisterLocationListener(mBaseLocationListenner)
        if (mLocationClient.isStarted) {
            mLocationClient.stop()
        }
    }
    class BaseLocationListener constructor(var baiDuUtils: BaiDuUtils) : BDAbstractLocationListener() {

        override fun onReceiveLocation(location: BDLocation?) {
            com.orhanobut.logger.Logger.i("百度定位返回结果  ---${location.toString()}")
            baiDuUtils.stop(this)
            if (null != location && location.locType != BDLocation.TypeServerError) {
                latitude = location.latitude
                longitude = location.longitude
                city = location.city
                province = location.province
                district = location.district
                addrStr = location.addrStr
                isHasSuccessLocation = true
                if (TextUtils.equals("4.9E-324", longitude.toString()) || TextUtils.equals("4.9E-324", latitude.toString())) {
                    latitude = 30.317662
                    longitude = 120.260282
                    city = "杭州市"
                    province = "浙江省"
                    district = "江干区"
                    addrStr = "浙江省杭州市江干区九环路九号"
                    isHasSuccessLocation = false
                }
            } else {
                isHasSuccessLocation = false
            }
        }
    }

}