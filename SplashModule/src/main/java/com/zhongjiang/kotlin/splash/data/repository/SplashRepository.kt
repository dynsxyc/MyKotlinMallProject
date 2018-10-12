package com.zhongjiang.kotlin.user.data.repository

import com.zhongjiang.kotlin.base.data.net.RetrofitFactory
import com.zhongjiang.kotlin.base.data.protocol.BaseResp
import com.zhongjiang.kotlin.user.data.api.SplashApi
import com.zhongjiang.kotlin.user.data.protocol.SplashAdReq
import org.json.JSONObject
import rx.Observable
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/16.
 * 直接访问网络
 */
open class SplashRepository @Inject constructor() {
    fun LoadAd(splashAdReq: SplashAdReq): Observable<BaseResp<JSONObject>> {
       return RetrofitFactory.instance.create(SplashApi:: class.java).LoadAd(splashAdReq)
    }

}