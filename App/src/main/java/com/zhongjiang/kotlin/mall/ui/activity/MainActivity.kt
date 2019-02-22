package com.zhongjiang.kotlin.mall.ui.activity

import android.os.Bundle
import com.jakewharton.rxbinding2.view.RxView
import com.zhongjiang.kotlin.base.busevent.LoginSuccessEvent
import com.zhongjiang.kotlin.base.ui.activity.BaseInjectActivity
import com.zhongjiang.kotlin.base.utils.RxLifecycleUtils
import com.zhongjiang.kotlin.mall.R
import com.zhongjiang.kotlin.provider.common.CommonUtils
import com.zhongjiang.kotlin.provider.router.NavigationUtil
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import javax.inject.Inject
import javax.inject.Singleton

class MainActivity : BaseInjectActivity(){

    @Inject
    @Singleton
    lateinit var commonUtils: CommonUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationUtil.navigationToLogin(false)
        setContentView(R.layout.activity_main)
        initView()
    }
    fun initView() {
        mRxBus.toObservable(LoginSuccessEvent::class.java, Consumer {
            toast("登录成功")
        }, RxLifecycleUtils.bindLifecycle(this))
        RxView.clicks(testLogout).subscribe{
            commonUtils.removeUserInfo()
        }
        RxView.clicks(testLogin).subscribe{
            NavigationUtil.navigationToLogin(true)
        }
    }


}
