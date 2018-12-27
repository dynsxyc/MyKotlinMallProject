package com.zhongjiang.kotlin.splash.ui.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhongjiang.kotlin.base.ui.activity.BaseInjectActivity
import com.zhongjiang.kotlin.base.widgets.listener.StackViewTouchListener
import com.zhongjiang.kotlin.provider.router.NavigationConstant
import com.zhongjiang.kotlin.provider.router.RouterPath
import com.zhongjiang.kotlin.splash.BuildConfig
import com.zhongjiang.kotlin.splash.R
import com.zhongjiang.kotlin.splash.ui.fragment.LoginFragment
import com.zhongjiang.kotlin.splash.ui.fragment.SplashFragment
import org.jetbrains.anko.dip

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
        initView()
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

    fun initView() {
        if (BuildConfig.DEBUG) {
            val root = findViewById<View>(android.R.id.content)
            if (root is FrameLayout) {
                val content = root as FrameLayout
                val stackView = ImageView(this)
                stackView.setImageResource(R.drawable.img_bug)
                val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                params.gravity = Gravity.BOTTOM
                val dp18 = dip(18)
                params.bottomMargin = dp18 * 7
                params.leftMargin = dp18
                params.width = dip(30)
                params.height = dip(30)
                stackView.layoutParams = params
                content.addView(stackView)
                stackView.setOnTouchListener(StackViewTouchListener(stackView, dp18 / 4))
                stackView.setOnClickListener {
                    //环境配置
                }
            }
        }
    }

}