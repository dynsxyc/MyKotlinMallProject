package com.zhongjiang.youxuan.base.injection.module

import android.content.Context
import com.alibaba.sdk.android.push.CloudPushService
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.orhanobut.logger.Logger
import com.zhongjiang.youxuan.base.common.BaseApplication
import com.zhongjiang.youxuan.base.injection.module.sheduler.AppSchedulerProvider
import com.zhongjiang.youxuan.base.injection.module.sheduler.SchedulerProvider
import com.zhongjiang.youxuan.base.oss.BucketType
import com.zhongjiang.youxuan.base.oss.OssService
import com.zhongjiang.youxuan.base.utils.ULogger
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by QingMei on 2017/8/14.
 * desc:
 */
@Module
class AppModule() {

    @Singleton
    @Provides
    fun provideApplication(): BaseApplication {
        return BaseApplication.AppContext.baseContext as BaseApplication
    }

    @Singleton
    @Provides
    fun provideSchedulers(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    fun provideBaiduLocationClientOption(): LocationClientOption {
        var mOption = LocationClientOption()
        /**可选，默认高精度，设置定位模式，Hight_Accuracy 高精度，Battery_Saving低功耗，Device_Sensors仅设备*/
        mOption.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        /**可选，设置返回经纬度坐标类型，默认GCJ02
        GCJ02：国测局坐标；
        BD09ll：百度经纬度坐标；
        BD09：百度墨卡托坐标；
        海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标*/
        mOption.setCoorType("BD09ll")
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setScanSpan(0)

        /**可选，设置是否使用gps，默认false
        使用高精度和仅用设备两种定位模式的，参数必须设置为true*/
        mOption.isOpenGps = true
        //可选，设置是否需要地址信息，默认不需要
        mOption.setIsNeedAddress(true)
        //可选，设置是否需要地址描述
        mOption.setIsNeedLocationDescribe(true)
        //可选，设置是否需要设备方向结果
        mOption.setNeedDeviceDirect(false)
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        mOption.isLocationNotify = false
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mOption.setIgnoreKillProcess(true)
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationDescribe(true)
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.setIsNeedLocationPoiList(true)
        //可选，默认false，设置是否收集CRASH信息，默认收集
        mOption.SetIgnoreCacheException(false)
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        mOption.setIsNeedAltitude(false)
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位
        mOption.setWifiCacheTimeOut(5 * 60 * 1000)
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        mOption.setEnableSimulateGps(false)
        mOption.isOpenGps = true
        return mOption
    }

    @Provides
    @Singleton
    fun provideBaiduLocationClient(application: BaseApplication, locationClientOption: LocationClientOption): LocationClient {
        var locationClient = LocationClient(application)
        locationClient.locOption = locationClientOption
        return locationClient
    }
    /**
     * 初始化云推送通道
     * @param application
     */
    @Provides
    @Singleton
    fun provideAliPushService(application: Context) : CloudPushService {
            PushServiceFactory.init(application)
            var pushService = PushServiceFactory.getCloudPushService();
            pushService.register(application, object : CommonCallback {
                override fun onSuccess(p0: String?) {
                    ULogger.i("init cloudchannel success $p0")
                }

                override fun onFailed(errorCode: String?, errorMessage: String?) {
                    ULogger.i("init cloudchannel failed -- errorcode:$errorCode errorMessage: $errorMessage")
                }

            })
        ULogger.i("deviceId = ${pushService.deviceId}")
        return pushService
    }

    @Provides
    @Singleton
    @Named("public")
    fun providePublicOsservice(context: BaseApplication, schedulerProvider: SchedulerProvider): OssService {
        return OssService(context, schedulerProvider, BucketType.BUCKET_CONFIT_TAG_PUBLIC)
    }

    @Provides
    @Singleton
    @Named("security")
    fun provideSecurityOsservice(context: BaseApplication, schedulerProvider: SchedulerProvider): OssService {
        return OssService(context, schedulerProvider, BucketType.BUCKET_CONFIT_TAG_SECURITY)
    }
}
