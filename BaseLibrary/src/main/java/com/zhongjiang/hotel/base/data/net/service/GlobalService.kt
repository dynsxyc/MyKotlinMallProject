package com.zhongjiang.hotel.base.data.net.service

import com.zhongjiang.youxuan.base.data.net.entity.UserInfo
import com.zhongjiang.youxuan.base.data.protocol.BaseResp

import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path

/**全局接口实现
 * 公共接口在这里  统一在baseModel中调用
 */
interface GlobalService {
    @GET("users/{user}")
    fun getUserInfo(@Path("user") user: String): Maybe<BaseResp<UserInfo>>
}
