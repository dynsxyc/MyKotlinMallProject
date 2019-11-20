package com.zhongjiang.youxuan.user.splash.ui.activity.splash

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import com.zhongjiang.youxuan.base.ui.activity.BaseInjectActivity
import com.zhongjiang.hotel.provider.common.CommonUtils
import com.zhongjiang.hotel.provider.router.NavigationConstant
import com.zhongjiang.hotel.provider.router.RouterPath
import com.zhongjiang.youxuan.user.splash.R
import com.zhongjiang.youxuan.user.splash.ui.fragment.login.LoginFragment
import com.zhongjiang.youxuan.user.splash.ui.fragment.splash.SplashFragment
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by dyn on 2018/7/25.
 */
@Route(path = RouterPath.SplashCenter.PATH_SPLASH_LOGIN)
class SplashActivity : BaseInjectActivity() {
    @Autowired(name = NavigationConstant.NAVIGATION_DATA_BOOLEAN)
    @JvmField
    var isToLogin: Boolean = false

    @Inject
    @Singleton
    lateinit var commonUtils: CommonUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isToLogin)
            loadRootFragment(R.id.mActivitySingleFragmentContent, LoginFragment.newInstance(true))
        else
            loadRootFragment(R.id.mActivitySingleFragmentContent, SplashFragment())
    }

    override fun injectRouter(): Boolean {
        return true
    }

    override fun getSwipeBackEnable(): Boolean {
        return false
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init()
    }

    override fun onBackPressedSupport() {
        commonUtils.appExit(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_single_fragment
    }
}