package com.zhongjiang.kotlin.splash.ui.activity

import android.os.Bundle
import com.zhongjiang.kotlin.base.ui.activity.BaseInjectActivity
import com.zhongjiang.kotlin.splash.R
import com.zhongjiang.kotlin.splash.ui.fragment.SplashFragment

/**
 * Created by dyn on 2018/7/25.
 */
class SplashActivity : BaseInjectActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        loadRootFragment(R.id.splash_content,SplashFragment.newInstance())
    }

    override fun getSwipeBackEnable(): Boolean {
        return false
    }
}