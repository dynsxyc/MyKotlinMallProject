package com.zhongjiang.kotlin.user.data.api

import com.zhongjiang.kotlin.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_APP_APLASH_AD
import com.zhongjiang.kotlin.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_APP_INSERT_OPEN_NUMBER
import com.zhongjiang.kotlin.base.data.protocol.BaseListResp
import com.zhongjiang.kotlin.base.data.protocol.BaseResp
import com.zhongjiang.kotlin.base.data.db.SplashAdEntity
import com.zhongjiang.kotlin.splash.service.protocol.AppStartStatisticsReq
import com.zhongjiang.kotlin.user.data.protocol.SplashAdReq
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by dyn on 2018/7/16.
 */
interface SplashService {
    /**启动页广告*/
    @POST(API_METHOD_APP_APLASH_AD)
    fun LoadAd(@Body splashAdReq: SplashAdReq): Maybe<BaseListResp<List<SplashAdEntity>>>

    /**APP打开次数*/
    @POST(API_METHOD_APP_INSERT_OPEN_NUMBER)
    fun appOpenStatistics(@Body appStartStatisticsReq: AppStartStatisticsReq): Maybe<BaseResp<String>>

}