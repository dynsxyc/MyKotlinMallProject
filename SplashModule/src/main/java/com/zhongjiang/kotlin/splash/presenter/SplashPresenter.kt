package com.zhongjiang.kotlin.splash.presenter

import com.zhongjiang.kotlin.base.ext.execute
import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.base.rx.BaseSubscriber
import com.zhongjiang.kotlin.splash.presenter.view.SplashView
import com.zhongjiang.kotlin.splash.service.impl.SplashServiceIml
import org.json.JSONObject
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/25.
 */
class SplashPresenter @Inject constructor() : BasePresenter<SplashView>() {
    @Inject
    lateinit var splashService : SplashServiceIml

    fun getSplashAd(){
        splashService.onLoadAd().execute(object :BaseSubscriber<JSONObject>(mView){
            override fun onNext(t: JSONObject) {
                    mView.onGetSplashAdResult(true)
                }
        },lifecycleProvider)
    }
}