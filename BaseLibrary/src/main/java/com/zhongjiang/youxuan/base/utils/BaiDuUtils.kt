package com.zhongjiang.youxuan.base.utils

import android.Manifest
import android.app.Activity
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.luck.picture.lib.permissions.RxPermissions
import com.orhanobut.logger.Logger
import com.zhongjiang.youxuan.base.data.db.BaiduLocationEntity
import io.objectbox.Box
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

    companion object {
        const val DEFAULT_LOCATION_ID = 100L
        const val DEFAULT_LATITUDE = 30.317662
        const val DEFAULT_LONGITUDE = 120.260282
        const val DEFAULT_CITY = "杭州市"
        const val DEFAULT_PROVINCE = "浙江省"
        const val DEFAULT_DISTRICT = "江干区"
        const val DEFAULT_ADDRESSSTR = ""
    }

    @Inject
    @Singleton
    lateinit var mLocationClient: LocationClient

    @Inject
    lateinit var baiduLocationBox: Box<BaiduLocationEntity>

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
    fun start(mContent:Activity,locationCallBackListener: LocationCallBackListener?) = synchronized(block) {
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


        override fun onReceiveLocation(location: BDLocation?) {
            var isHasSuccessLocation = 0
            var latitude = DEFAULT_LATITUDE
            var longitude = DEFAULT_LONGITUDE
            var city = DEFAULT_CITY
            var province = DEFAULT_PROVINCE
            var district = DEFAULT_DISTRICT
            var addressStr = DEFAULT_ADDRESSSTR
            location?.let {
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
            var baiduLocationEntity = BaiduLocationEntity(isHasSuccessLocation, latitude, latitude.toString(), longitude, longitude.toString(), city, province, district, addressStr)
            if (baiduLocationBox.count() <= 0) {
                baiduLocationBox.put(baiduLocationEntity)
            } else {
                var newLocation =baiduLocationBox.get(DEFAULT_LOCATION_ID).clone(baiduLocationEntity)
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

    private fun getLocationEntity(): BaiduLocationEntity? {
        baiduLocationBox?.let {
            if (baiduLocationBox.count() <= 0) {
                return null
            } else {
                return baiduLocationBox.get(DEFAULT_LOCATION_ID)
            }
        }
    }

    /**
     * 是否包含有成功的定位信息
     */
    fun isHasSuccessLocation(): Boolean {
        if (getLocationEntity() != null) {
            return getLocationEntity()!!.hasSuccessLocation == 1
        } else {
            return false
        }
    }

    /**
     * gps 获取当前位置的【纬度】
     */
    fun getLatitude(): Double {
        if (getLocationEntity() != null) {
            return getLocationEntity()!!.latitude
        } else {
            return DEFAULT_LATITUDE
        }
    }

    fun getLatitudeStr(): String {
        if (getLocationEntity() != null) {
            return getLocationEntity()!!.latitudeStr
        } else {
            return DEFAULT_LATITUDE.toString()
        }
    }

    /**
     * gps 获取当前位置的【经度】
     */
    fun getLongitude(): Double {
        if (getLocationEntity() != null) {
            return getLocationEntity()!!.longitude
        } else {
            return DEFAULT_LONGITUDE
        }
    }

    fun getLongitudeStr(): String {
        if (getLocationEntity() != null) {
            return getLocationEntity()!!.longitudeStr
        } else {
            return DEFAULT_LONGITUDE.toString()
        }
    }

    /**
     * 获取当前位置的城市
     */
    fun getCity(): String {
        if (getLocationEntity() != null) {
            return getLocationEntity()!!.city
        } else {
            return DEFAULT_CITY
        }
    }

    /**
     * 获取当前位置的省
     */
    fun getProvince(): String {
        if (getLocationEntity() != null) {
            return getLocationEntity()!!.province
        } else {
            return DEFAULT_PROVINCE
        }
    }

    /**
     * 获取当前位置的区
     */
    fun getDistrict(): String {
        if (getLocationEntity() != null) {
            return getLocationEntity()!!.district
        } else {
            return DEFAULT_DISTRICT
        }
    }

    /**
     * 获取当前位置的详细地址信息
     */
    fun getAddressStr(): String {
        if (getLocationEntity() != null) {
            return getLocationEntity()!!.addressStr
        } else {
            return DEFAULT_ADDRESSSTR
        }
    }

    override fun toString(): String {
        return "${getLatitudeStr()} --------- ${getLongitudeStr()} --------- ${getCity()} --------- ${getProvince()} --------- ${getDistrict()} --------- ${getAddressStr()} --------- ${isHasSuccessLocation()}"
    }


}