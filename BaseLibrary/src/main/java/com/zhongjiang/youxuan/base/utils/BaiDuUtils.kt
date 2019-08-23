package com.zhongjiang.youxuan.base.utils

import android.Manifest
import android.app.Activity
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.luck.picture.lib.permissions.RxPermissions
import com.orhanobut.logger.Logger
import com.zhongjiang.youxuan.base.data.db.BaiDuLocationEntity
import io.objectbox.Box
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @date on 2019/5/10 17:08
 * @packagename com.zhongjiang.youxuan.base.utils
 * @author dyn
 * @fileName BaiDuUtils
 * @org com.zhongjiang.youxuan
 * @describe 百度定位数据
 * @email 583454199@qq.com
 **/
class BaiDuUtils @Inject constructor() {

    companion object {
        const val DEFAULT_LOCATION_ID = 100L
        const val DEFAULT_LATITUDE = 30.317662
        const val DEFAULT_LONGITUDE = 120.260282
        const val DEFAULT_CITY = "杭州市"
        const val DEFAULT_PROVINCE = "浙江省"
        const val DEFAULT_DISTRICT = "江干区"
        const val DEFAULT_ADDRESSSTR = ""
        val DEFAULT_BAIDU_ENTITY = BaiDuLocationEntity(0, DEFAULT_LATITUDE, DEFAULT_LATITUDE.toString(), DEFAULT_LONGITUDE, DEFAULT_LONGITUDE.toString(), DEFAULT_CITY, DEFAULT_PROVINCE, DEFAULT_DISTRICT, DEFAULT_ADDRESSSTR);
    }

    @Inject
    @Singleton
    lateinit var mLocationClient: LocationClient

    @Inject
    lateinit var baiduLocationBox: Box<BaiDuLocationEntity>

    private var mBaseLocationListener = BaseLocationListener()
    private fun registerLocationListener(baseLocationListener: BaseLocationListener) {
        this.mLocationClient.registerLocationListener(baseLocationListener)
    }

    private fun unRegisterLocationListener(baseLocationListener: BaseLocationListener) {
        try {
            this.mLocationClient.unRegisterLocationListener(baseLocationListener)
        } catch (e: Exception) {

        }
    }

    private var block = "block"
    fun start(mContent: Activity, locationCallBackListener: LocationCallBackListener?) = synchronized(block) {
        RxPermissions(mContent).requestEach(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).subscribe {
            if (it.granted) {
                //同意
            } else if (it.shouldShowRequestPermissionRationale) {
                //拒绝权限再问一次T
            } else {
                //拒绝权限，不再问任何问题
                //需要转到设置
                locationCallBackListener?.onLackLocationPermissions()
            }
        }
        startLocation(locationCallBackListener)
    }

    private fun startLocation(locationCallBackListener: LocationCallBackListener?) {
        registerLocationListener(mBaseLocationListener)
        mBaseLocationListener.let { locationListener ->
            locationCallBackListener?.let {
                locationListener.callBackListener = it
            }
        }
        this.mLocationClient.let {
            if (mLocationClient.isStarted) {
                mLocationClient.restart()
            } else {
                mLocationClient.start()
            }
        }

    }

    private fun stopLocation() = synchronized(block) {
        if (mLocationClient.isStarted) {
            mLocationClient.stop()
        }
        unRegisterLocationListener(mBaseLocationListener)
    }

    inner class BaseLocationListener : BDAbstractLocationListener() {
        var callBackListener: LocationCallBackListener? = null
        override fun onReceiveLocation(location: BDLocation) {
            var isHasSuccessLocation = 0
            var latitude = DEFAULT_LATITUDE
            var longitude = DEFAULT_LONGITUDE
            var city = DEFAULT_CITY
            var province = DEFAULT_PROVINCE
            var district = DEFAULT_DISTRICT
            var addressStr = DEFAULT_ADDRESSSTR
            location.let {
                Logger.i("百度定位返回结果  ---${it.latitude} ---${it.longitude} ---${it.city} ---${it.province} ---${it.district} ---${it.addrStr} ---${it.locType} ")
                when (it.locType) {
                    BDLocation.TypeNetWorkLocation, BDLocation.TypeGpsLocation, BDLocation.TypeOffLineLocation -> {
                        //网络定位、GPS定位 、离线定位  状态代表定位有结果 定位成功
                        latitude = it.latitude
                        longitude = it.longitude
                        city = it.city
                        province = it.province
                        district = it.district
                        addressStr = it.addrStr
                        isHasSuccessLocation = 1
                        callBackListener?.let { listener ->
                            listener.onLocationCallback(true)
                        }
                    }
                    else -> {
                        callBackListener?.let { listener ->
                            listener.onLocationCallback(false)
                        }
                    }
                }
            }
            var baiduLocationEntity = BaiDuLocationEntity(isHasSuccessLocation, latitude, latitude.toString(), longitude, longitude.toString(), city, province, district, addressStr)
            if (baiduLocationBox.count() <= 0) {
                baiduLocationBox.put(baiduLocationEntity)
            } else {
                var newLocation = baiduLocationBox.get(DEFAULT_LOCATION_ID).clone(baiduLocationEntity)
                baiduLocationBox.put(newLocation)
            }
            Logger.i("baiduLocationBox count = ${baiduLocationBox.count()}")
            stopLocation()
        }
    }

    interface LocationCallBackListener {
        fun onLocationCallback(isSuccess: Boolean)
        fun onLackLocationPermissions()
    }

    private fun getLocationEntity(): BaiDuLocationEntity {
        baiduLocationBox?.let {
            if (baiduLocationBox.count() <= 0) {
                return DEFAULT_BAIDU_ENTITY
            } else {
                return baiduLocationBox.get(DEFAULT_LOCATION_ID)
            }
        }
    }

    /**
     * 是否包含有成功的定位信息
     */
    fun isHasSuccessLocation(): Boolean {
        return getLocationEntity().hasSuccessLocation == 1
    }

    /**
     * gps 获取当前位置的【纬度】
     */
    fun getLatitude(): Double {
        return getLocationEntity().latitude
    }

    fun getLatitudeStr(): String {
        return getLocationEntity().latitudeStr
    }

    /**
     * gps 获取当前位置的【经度】
     */
    fun getLongitude(): Double {
        return getLocationEntity().longitude
    }

    fun getLongitudeStr(): String {
        return getLocationEntity().longitudeStr
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
        return getLocationEntity().addressStr
    }

    override fun toString(): String {
        return "${getLatitudeStr()} --------- ${getLongitudeStr()} --------- ${getCity()} --------- ${getProvince()} --------- ${getDistrict()} --------- ${getAddressStr()} --------- ${isHasSuccessLocation()}"
    }


}