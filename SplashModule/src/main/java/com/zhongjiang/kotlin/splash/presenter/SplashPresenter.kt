package com.zhongjiang.kotlin.splash.presenter

import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.splash.presenter.view.SplashView
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/25.
 */
class SplashPresenter @Inject constructor() : BasePresenter<SplashView>() {
//    @Inject
//    lateinit var splashService : SplashServiceImpl

    fun getSplashAd(){
//        splashService.onLoadAd().execute(object :BaseSubscriber<JSONObject>(mView){
//            override fun onNext(t: JSONObject) {
//                    mView.onGetSplashAdResult(true)
//                }
//        },lifecycleProvider)
    }
}