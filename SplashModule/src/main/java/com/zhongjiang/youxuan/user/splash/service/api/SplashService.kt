package com.zhongjiang.youxuan.user.data.api

import com.zhongjiang.youxuan.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_CODE
import com.zhongjiang.youxuan.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_REGCODE
import com.zhongjiang.youxuan.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_USERNAME
import com.zhongjiang.youxuan.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_APP_APLASH_AD
import com.zhongjiang.youxuan.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_APP_INSERT_OPEN_NUMBER
import com.zhongjiang.youxuan.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_LOGINSENDREGCODE
import com.zhongjiang.youxuan.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_LOGIN_REGCODE
import com.zhongjiang.youxuan.base.data.db.SplashAdEntity
import com.zhongjiang.youxuan.base.data.db.UserInfoEntity
import com.zhongjiang.youxuan.base.data.net.service.BaseServiceManager
import com.zhongjiang.youxuan.base.data.protocol.BaseList
import com.zhongjiang.youxuan.base.data.protocol.BaseResp
import com.zhongjiang.youxuan.base.data.protocol.PageReq
import com.zhongjiang.youxuan.user.splash.data.VerificationCodeResuleInfo
import com.zhongjiang.youxuan.user.splash.service.protocol.AppStartStatisticsReq
import com.zhongjiang.youxuan.user.data.protocol.SplashAdReq
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by dyn on 2018/7/16.
 */
interface SplashService {
    /**启动页广告*/
    @POST("app/qt/appStartPage/queryList")
    fun loadAd(@Body pageReq: PageReq = PageReq(1,1)): Maybe<BaseResp<BaseList<List<SplashAdEntity>>>>

    /**APP打开次数*/
    @POST("app/other/openApp/insertOpenNumber")
    fun appOpenStatistics(@Body appStartStatisticsReq: AppStartStatisticsReq): Maybe<BaseResp<String>>

    @POST("app/appUser/user/sendRegCode")
    fun loginGetVerificationCode(@Query(API_DATA_FIELD_USERNAME) phoneNumber:String):Maybe<BaseResp<VerificationCodeResuleInfo>>

    @POST("app/appUser/user/loginRegCode")
    fun userLogin(@Query(API_DATA_FIELD_CODE) code:String, @Query(API_DATA_FIELD_REGCODE) regCode:String, @Query(API_DATA_FIELD_USERNAME) phoneNumber:String):Maybe<BaseResp<UserInfoEntity>>

}