package com.zhongjiang.kotlin.splash.service

import org.json.JSONObject
import rx.Observable

/**
 * Created by dyn on 2018/7/25.
 */
interface SplashService {
    fun onLoadAd(): Observable<JSONObject>
}