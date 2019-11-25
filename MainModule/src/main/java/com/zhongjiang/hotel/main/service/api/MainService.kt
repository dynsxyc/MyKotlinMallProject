package com.zhongjiang.hotel.main.service.api

import com.zhongjiang.hotel.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_CODE
import com.zhongjiang.hotel.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_REGCODE
import com.zhongjiang.hotel.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_USERNAME
import com.zhongjiang.hotel.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_APP_APLASH_AD
import com.zhongjiang.hotel.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_APP_INSERT_OPEN_NUMBER
import com.zhongjiang.hotel.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_LOGINSENDREGCODE
import com.zhongjiang.hotel.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_LOGIN_REGCODE
import com.zhongjiang.hotel.base.data.protocol.BaseList
import com.zhongjiang.hotel.base.data.protocol.BaseResp
import com.zhongjiang.hotel.main.data.VerificationCodeResuleInfo
import com.zhongjiang.hotel.main.service.protocol.AppStartStatisticsReq
import com.zhongjiang.hotel.main.service.protocol.SplashAdReq
import com.zhongjiang.hotel.provider.db.SplashAdEntity
import com.zhongjiang.hotel.provider.db.UserInfoEntity
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by dyn on 2018/7/16.
 */
interface MainService  {
    /**启动页广告*/
    @POST(API_METHOD_APP_APLASH_AD)
    fun loadAd(@Body splashAdReq: SplashAdReq): Maybe<BaseResp<BaseList<List<SplashAdEntity>>>>

    /**APP打开次数*/
    @POST(API_METHOD_APP_INSERT_OPEN_NUMBER)
    fun appOpenStatistics(@Body appStartStatisticsReq: AppStartStatisticsReq): Maybe<BaseResp<String>>

    @POST(API_METHOD_LOGINSENDREGCODE)
    fun loginGetVerificationCode(@Query(API_DATA_FIELD_USERNAME) phoneNumber:String):Maybe<BaseResp<VerificationCodeResuleInfo>>

    @POST(API_METHOD_LOGIN_REGCODE)
    fun userLogin(@Query(API_DATA_FIELD_CODE) code:String, @Query(API_DATA_FIELD_REGCODE) regCode:String, @Query(API_DATA_FIELD_USERNAME) phoneNumber:String):Maybe<BaseResp<UserInfoEntity>>

}