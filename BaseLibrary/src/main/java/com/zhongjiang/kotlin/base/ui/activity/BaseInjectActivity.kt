package com.zhongjiang.kotlin.base.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

open abstract class BaseInjectActivity : BaseSupportActivity(), HasFragmentInjector, HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<android.app.Fragment>
    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
        return fragmentInjector
    }

    override fun onStart() {
        super.onStart()
        setSwipeBackEnable(true)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        inject();
        super.onCreate(savedInstanceState)
    }

    private fun inject() {
        AndroidInjection.inject(this)
        if (injectRouter()) {
            ARouter.getInstance().inject(this)
        }
    }

    open  fun injectRouter(): Boolean {
        return false
    }
}