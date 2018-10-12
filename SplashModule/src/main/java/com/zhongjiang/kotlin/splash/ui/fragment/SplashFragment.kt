package com.zhongjiang.kotlin.splash.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhongjiang.kotlin.base.ui.fragment.BaseMvpFragment
import com.zhongjiang.kotlin.splash.R
import com.zhongjiang.kotlin.splash.presenter.SplashPresenter
import com.zhongjiang.kotlin.splash.presenter.view.SplashView
import com.zhongjiang.kotlin.user.injection.component.DaggerSplashComponent
import com.zhongjiang.kotlin.user.injection.module.SplashModule
import org.jetbrains.anko.support.v4.toast

/**
 * Created by dyn on 2018/7/25.
 */
class SplashFragment :BaseMvpFragment<SplashPresenter>() ,SplashView {
    override fun onGetSplashAdResult(result: Boolean) {
        toast("接口获取成功")
    }

    override fun injectComponent() {
        DaggerSplashComponent.builder().activityComponent(activityComponent).splashModule(SplashModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view :View = inflater.inflate(R.layout.fragment_splash,null)
        return attachToSwipeBack(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.getSplashAd()
    }

}