package com.zhongjiang.kotlin.splash.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import com.zhongjiang.kotlin.splash.ui.fragment.LoginFragment
import com.zhongjiang.kotlin.splash.ui.fragment.SplashFragment
import com.zhongjiang.youxuan.base.ui.activity.BaseInjectActivity
import com.zhongjiang.youxuan.provider.router.NavigationConstant
import com.zhongjiang.youxuan.provider.router.RouterPath
import com.zhongjiang.youxuan.user.splash.R

/**
 * Created by dyn on 2018/7/25.
 */
@Route(path = RouterPath.SplashCenter.PATH_SPLASH_LOGIN)
class SplashActivity : BaseInjectActivity() {
    @Autowired(name = NavigationConstant.NAVIGATION_DATA_BOOLEAN)
    @JvmField
    var isToLogin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (isToLogin)
            loadRootFragment(R.id.splash_content, LoginFragment.newInstance(true))
        else
            loadRootFragment(R.id.splash_content, SplashFragment.newInstance())
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
}