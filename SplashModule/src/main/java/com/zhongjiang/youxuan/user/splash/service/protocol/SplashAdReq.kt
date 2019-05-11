package com.zhongjiang.youxuan.user.data.protocol

import com.zhongjiang.youxuan.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_APPTYPE
import com.zhongjiang.youxuan.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_PAGE
import com.zhongjiang.youxuan.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_PAGESIZE
import retrofit2.http.Field

/**
 * Created by dyn on 2018/7/16.
 */
data class SplashAdReq(@Field(API_DATA_FIELD_PAGE) val page:String, @Field(API_DATA_FIELD_PAGESIZE) val pageSize:String, @Field(API_DATA_FIELD_APPTYPE) val  appType:String)