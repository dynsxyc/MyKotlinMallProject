package com.zhongjiang.kotlin.splash.service.impl

import com.zhongjiang.kotlin.base.ext.convert
import com.zhongjiang.kotlin.splash.service.SplashService
import com.zhongjiang.kotlin.user.data.protocol.SplashAdReq
import com.zhongjiang.kotlin.user.data.repository.SplashRepository
import org.json.JSONObject
import rx.Observable
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/25.
 */
class SplashServiceImpl @Inject constructor() : SplashService {
    @Inject
    lateinit var splashRepository : SplashRepository

    override fun onLoadAd(): Observable<JSONObject> {
//        return splashRepository.LoadAd(SplashAdReq("1","1","10")).ob
        return Observable.just(null);
    }
}