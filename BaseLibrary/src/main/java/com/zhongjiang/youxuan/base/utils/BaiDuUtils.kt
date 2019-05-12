package com.zhongjiang.youxuan.base.utils

import android.text.TextUtils
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @date on 2019/5/10 17:08
 * @packagename com.zhongjiang.youxuan.base.utils
 * @author dyn
 * @fileName BaiDuUtils
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
class BaiDuUtils @Inject constructor() {

    @Inject
    @Singleton
    lateinit var mLocationClient: LocationClient
    /**
     * 是否包含有成功的定位信息
     */
    var isHasSuccessLocation = false
    /**
     * gps 获取当前位置的【纬度】
     */
    var latitude = 30.317662
    var latitudeStr = latitude.toString()
    /**
     * gps 获取当前位置的【经度】
     */
    var longitude = 120.260282
    var longitudeStr = longitude.toString()
    /**
     * 获取当前位置的城市
     */
    var city = "杭州市"
    /**
     * 获取当前位置的省
     */
    var province = "浙江省"
    /**
     * 获取当前位置的区
     */
    var district = "江干区"
    /**
     * 获取当前位置的详细地址信息
     */
    var addrStr: String? = null
    var mBaseLocationListener = BaseLocationListener(this)
    fun registerLocationListener(baseLocationListener: BaseLocationListener){
        this.mLocationClient.registerLocationListener(baseLocationListener)
    }
    fun unRegisterLocationListener(baseLocationListener: BaseLocationListener){
        try {
            this.mLocationClient.unRegisterLocationListener(baseLocationListener)
        }catch (e:Exception){

        }
    }
    fun start(locationCallBackListener: LocationCallBackListener?) {
        registerLocationListener(mBaseLocationListener)
        mBaseLocationListener?.let { locationListener->
            locationCallBackListener?.let {
                locationListener.callBackListener = it
            }
        }
        this.mLocationClient.start()
    }

    fun stop() {
        if (mLocationClient.isStarted) {
            mLocationClient.stop()
        }
    }

    class BaseLocationListener constructor(private val baiDuUtils: BaiDuUtils) : BDAbstractLocationListener() {
         lateinit var callBackListener : LocationCallBackListener


        override fun onReceiveLocation(location: BDLocation?) {
            baiDuUtils.stop()
            baiDuUtils.isHasSuccessLocation = false
            location?.let {
                com.orhanobut.logger.Logger.i("百度定位返回结果  ---${it.latitude} ---${it.longitude} ---${it.city} ---${it.province} ---${it.district} ---${it.addrStr} ---${it.locType} ")
                com.orhanobut.logger.Logger.i("百度定位返回结果utils  ---${baiDuUtils.latitude} ---${baiDuUtils.longitude} ---${baiDuUtils.city} ---${baiDuUtils.province} ---${baiDuUtils.district} ---${baiDuUtils.addrStr} ")

                when(it.locType){
                    BDLocation.TypeNetWorkLocation,BDLocation.TypeGpsLocation,BDLocation.TypeOffLineLocation->{
                        //网络定位、GPS定位 、离线定位  状态代表定位有结果 定位成功
                        baiDuUtils.latitude = it.latitude
                        baiDuUtils.longitude = it.longitude
                        baiDuUtils.city = it.city
                        baiDuUtils.province = it.province
                        baiDuUtils.district = it.district
                        baiDuUtils.addrStr = it.addrStr
                        baiDuUtils.isHasSuccessLocation = true
                        callBackListener?.let { listener->
                            listener.onCallback(true)
                        }
                    }
                    else ->{
                        callBackListener?.let { listener->
                            listener.onCallback(false)
                        }
                    }
                }
            }
        }
    }
    interface LocationCallBackListener{
        fun onCallback(isSuccess : Boolean)
    }

}