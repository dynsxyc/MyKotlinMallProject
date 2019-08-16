package com.zhongjiang.kotlin.user.data.api

import com.zhongjiang.kotlin.splash.data.VerificationCodeResuleInfo
import com.zhongjiang.kotlin.splash.service.protocol.AppStartStatisticsReq
import com.zhongjiang.kotlin.user.data.protocol.SplashAdReq
import com.zhongjiang.youxuan.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_CODE
import com.zhongjiang.youxuan.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_REGCODE
import com.zhongjiang.youxuan.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_USERNAME
import com.zhongjiang.youxuan.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_APP_APLASH_AD
import com.zhongjiang.youxuan.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_APP_INSERT_OPEN_NUMBER
import com.zhongjiang.youxuan.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_LOGINSENDREGCODE
import com.zhongjiang.youxuan.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_LOGIN_REGCODE
import com.zhongjiang.youxuan.base.data.db.SplashAdEntity
import com.zhongjiang.youxuan.base.data.db.UserInfoEntity
import com.zhongjiang.youxuan.base.data.protocol.BaseList
import com.zhongjiang.youxuan.base.data.protocol.BaseResp
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by dyn on 2018/7/16.
 */
interface SplashService  {
    /**启动页广告*/
    @POST(API_METHOD_APP_APLASH_AD)
    fun LoadAd(@Body splashAdReq: SplashAdReq): Maybe<BaseResp<BaseList<List<SplashAdEntity>>>>

    /**APP打开次数*/
    @POST(API_METHOD_APP_INSERT_OPEN_NUMBER)
    fun appOpenStatistics(@Body appStartStatisticsReq: AppStartStatisticsReq): Maybe<BaseResp<String>>

    @POST(API_METHOD_LOGINSENDREGCODE)
    fun loginGetVerificationCode(@Query(API_DATA_FIELD_USERNAME) phoneNumber:String):Maybe<BaseResp<VerificationCodeResuleInfo>>

    @POST(API_METHOD_LOGIN_REGCODE)
    fun userLogin(@Query(API_DATA_FIELD_CODE) code:String, @Query(API_DATA_FIELD_REGCODE) regCode:String, @Query(API_DATA_FIELD_USERNAME) phoneNumber:String):Maybe<BaseResp<UserInfoEntity>>

}