package com.zhongjiang.kotlin.user.data.api

import com.zhongjiang.kotlin.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_APP_APLASH_AD
import com.zhongjiang.kotlin.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_APP_INSERT_OPEN_NUMBER
import com.zhongjiang.kotlin.base.data.protocol.BaseResp
import com.zhongjiang.kotlin.splash.data.protocol.AppStartStatisticsReq
import com.zhongjiang.kotlin.user.data.protocol.SplashAdReq
import org.json.JSONObject
import java.util.*

/**
 * Created by dyn on 2018/7/16.
 */
interface SplashApi {
    /**启动页广告*/
    @POST(API_METHOD_APP_APLASH_AD)
    fun LoadAd(@Body splashAdReq: SplashAdReq): Observable<BaseResp<JSONObject>>
    /**启动页广告*/
    /**APP打开次数*/
    @POST(API_METHOD_APP_INSERT_OPEN_NUMBER)
    fun appOpenStatistics(@Body appStartStatisticsReq: AppStartStatisticsReq): Observable<BaseResp<String>>

}