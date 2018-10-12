package com.zhongjiang.kotlin.user.data.api

import com.zhongjiang.kotlin.base.data.protocol.BaseResp
import retrofit2.http.GET
import rx.Observable

/**
 * Created by dyn on 2018/7/16.
 */
interface UploadApi {
    @GET("get")
    fun getUploadToken(): Observable<BaseResp<String>>
}