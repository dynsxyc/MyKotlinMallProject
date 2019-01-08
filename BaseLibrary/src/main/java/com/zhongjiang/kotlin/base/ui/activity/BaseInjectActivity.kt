package com.zhongjiang.kotlin.base.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.Logger
import com.zhongjiang.kotlin.base.busevent.ActivityResultEvent
import com.zhongjiang.kotlin.base.utils.RxBus
import com.zhongjiang.kotlin.base.utils.StatusBarUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import javax.inject.Singleton

open abstract class BaseInjectActivity : BaseSupportActivity(), HasFragmentInjector, HasSupportFragmentInjector {
    val TAG = this.javaClass.name
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<android.app.Fragment>
    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @Singleton
    lateinit var mRxBus: RxBus

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
        return fragmentInjector
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        if (isDarkModeStatus())
            darkModeStatus()
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
    /**沉浸式状态栏*/
    private fun darkModeStatus(){
        StatusBarUtil.darkMode(this)
    }

    protected fun isDarkModeStatus():Boolean{
        return  true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Logger.i("registerActivityResultEvent isObserver ${mRxBus.isObserver()}")
        mRxBus.post(ActivityResultEvent(requestCode,resultCode,data))
        super.onActivityResult(requestCode, resultCode, data)
    }

}