package com.zhongjiang.hotel.base.utils

import android.Manifest
import android.app.Activity
import android.os.Handler
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.luck.picture.lib.permissions.RxPermissions
import com.zhongjiang.hotel.base.data.BaiDuLocationEntity
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @date on 2019/5/10 17:08
 * @packagename com.zhongjiang.youxuan.base.utils
 * @author dyn
 * @fileName BaiDuLocationManager
 * @org com.zhongjiang.youxuan
 * @describe 百度定位数据
 * @email 583454199@qq.com
 **/
class BaiDuLocationManager @Inject constructor() {

    @Inject
    @Singleton
    lateinit var mLocationClient: LocationClient

    var mLocationEntity = PreferenceSettings.fromJson(PreferenceSettings.mBaiDuLocation,BaiDuLocationEntity::class.java)

    private val listeners = ArrayList<LocationCallBackListener>()

    private var mBaseLocationListener = object :BDAbstractLocationListener(){
        override fun onReceiveLocation(it: BDLocation) {
                ULogger.i("百度定位返回结果  ---${it.latitude} ---${it.longitude} ---${it.city} ---${it.province} ---${it.district} ---${it.addrStr} ---${it.locType} ")
                when (it.locType) {
                    BDLocation.TypeNetWorkLocation, BDLocation.TypeGpsLocation, BDLocation.TypeOffLineLocation -> {
                        //网络定位、GPS定位 、离线定位  状态代表定位有结果 定位成功
                        mLocationEntity.isSuccessLocation = true
                        mLocationEntity.latitude = it.latitude
                        mLocationEntity.longitude = it.longitude
                        mLocationEntity.city = it.city
                        mLocationEntity.district = it.district
                        mLocationEntity.address = it.addrStr
                        mLocationEntity.province = it.province
                        onListenerCallBack(true)
                    }
                    else -> {
                        onListenerCallBack(false)
                        mLocationEntity.isSuccessLocation = false
                    }
                }
            PreferenceSettings.mBaiDuLocation = PreferenceSettings.toJson(mLocationEntity)
            stopLocation()
        }
    }

    private var block = "block"
    fun start(mContent: Activity, callBackListener: LocationCallBackListener?){
        RxPermissions(mContent).requestEach(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).subscribe {
            if (it.granted) {
                //同意
            } else if (it.shouldShowRequestPermissionRationale) {
                //拒绝权限再问一次T
            }
        }
        callBackListener?.let {
            listeners.add(callBackListener)
        }
        startLocation()
    }

    private fun startLocation(){
        if (mLocationClient != null && !mLocationClient.isStarted()) {
            mLocationClient.registerLocationListener(mBaseLocationListener)
            mLocationClient.start()
        } else {
            Handler().postDelayed({
                stopLocation()
                startLocation()
            }, 3000)
        }
    }


    private fun stopLocation(){
            mLocationClient.stop()
        mLocationClient.unRegisterLocationListener(mBaseLocationListener)
    }

    interface LocationCallBackListener {
        fun onLocationCallback(isSuccess: Boolean)
    }

    private fun getLocationEntity(): BaiDuLocationEntity {
            return mLocationEntity;
    }

    /**
     * gps 获取当前位置的【纬度】
     */
    fun getLatitude(): Double {
        return getLocationEntity().latitude
    }

    fun getLatitudeStr(): String {
        return getLocationEntity().latitude.toString()
    }

    /**
     * gps 获取当前位置的【经度】
     */
    fun getLongitude(): Double {
        return getLocationEntity().longitude
    }

    fun getLongitudeStr(): String {
        return getLocationEntity().longitude.toString()
    }

    /**
     * 获取当前位置的城市
     */
    fun getCity(): String {
        return getLocationEntity().city
    }

    /**
     * 获取当前位置的省
     */
    fun getProvince(): String {
        return getLocationEntity().province
    }

    /**
     * 获取当前位置的区
     */
    fun getDistrict(): String {
        return getLocationEntity().district
    }

    /**
     * 获取当前位置的详细地址信息
     */
    fun getAddressStr(): String {
        return getLocationEntity().address
    }

    private fun onListenerCallBack(isSuccess: Boolean) {
        listeners.forEach {
            it.onLocationCallback(isSuccess)
        }
        listeners.clear()
        }



}