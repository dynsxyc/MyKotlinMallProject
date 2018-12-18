package com.zhongjiang.kotlin.splash.presenter

import android.util.Log
import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.splash.presenter.contract.SplashContract
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/25.
 */
class SplashPresenter @Inject constructor(view: SplashContract.View,model: SplashContract.Model) : BasePresenter<SplashContract.View,SplashContract.Model>(view,model),SplashContract.Presenter {
    override fun requestUserInfo(name: String) {
        mModel.requestUserInfo(name)
                .doOnDispose { Log.d("test", "HomePresenter.requestUserInfo.dispose") }
                .`as`(bindLifecycle())
                .subscribe({ info -> mView.onGetUserInfo(info) },
                        { e -> mView.onError("请求出现异常") })
    }

}