package com.zhongjiang.youxuan.user.data.api

import com.zhongjiang.youxuan.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_CODE
import com.zhongjiang.youxuan.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_REGCODE
import com.zhongjiang.youxuan.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_USERNAME
import com.zhongjiang.youxuan.base.common.YouXuanNetInterfaceConstant.Companion.API_METHOD_LOGIN_REGCODE
import com.zhongjiang.youxuan.base.data.db.UserInfoEntity
import com.zhongjiang.youxuan.base.data.protocol.BaseResp
import io.reactivex.Maybe
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by dyn on 2018/7/16.
 */
interface MainService  {

    @POST(API_METHOD_LOGIN_REGCODE)
    fun userLogin(@Query(API_DATA_FIELD_CODE) code:String, @Query(API_DATA_FIELD_REGCODE) regCode:String, @Query(API_DATA_FIELD_USERNAME) phoneNumber:String): Maybe<BaseResp<UserInfoEntity>>

}