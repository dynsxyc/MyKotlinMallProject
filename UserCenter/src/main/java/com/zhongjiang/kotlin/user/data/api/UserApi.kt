package com.zhongjiang.kotlin.user.data.api

import com.zhongjiang.kotlin.base.data.protocol.BaseResp
import com.zhongjiang.kotlin.user.data.protocol.RegisterReq
import com.zhongjiang.kotlin.user.data.protocol.UserInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import rx.Observable

/**
 * Created by dyn on 2018/7/16.
 */
interface UserApi {
    @GET("app/user/2.0/getUserInfo")
    fun register(@Query("id") mobile:String,@Query("pwd") pwd:String,@Query("verifyCode") verifyCode:String):Observable<BaseResp<String>>
    @POST("app/user/2.0/getUserInfo")
    fun login(@Body registerReq: RegisterReq):Observable<BaseResp<UserInfo>>
    @GET("get")
    fun forgetPwd(@Query("mobile") mobile:String,@Query("verifyCode") verifyCode:String):Observable<BaseResp<String>>
    @GET("get")
    fun resetPwd(@Query("mobile") mobile:String,@Query("pwd") pwd:String):Observable<BaseResp<String>>
    @GET("get")
    fun editUser(@Query("userIcon") userIcon:String,@Query("userName") userNane:String,@Query("userGender") userGender:String,@Query("userSign") userSign:String):Observable<BaseResp<UserInfo>>
}
