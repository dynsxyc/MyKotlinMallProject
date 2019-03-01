package com.zhongjiang.kotlin.mall.ui.activity

import android.os.Bundle
import com.orhanobut.logger.Logger
import com.zhongjiang.kotlin.base.busevent.LoginSuccessEvent
import com.zhongjiang.kotlin.base.ui.activity.BaseInjectActivity
import com.zhongjiang.kotlin.base.utils.RxLifecycleUtils
import com.zhongjiang.kotlin.base.utils.StatusBarUtil
import com.zhongjiang.kotlin.mall.R
import com.zhongjiang.kotlin.mall.ui.fragment.MainFragment
import com.zhongjiang.kotlin.provider.router.NavigationUtil
import io.reactivex.functions.Consumer
import org.jetbrains.anko.toast

class MainActivity : BaseInjectActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i(TAG,"onCreate");
        NavigationUtil.navigationToLogin(false)
        setContentView(R.layout.activity_main)

        initView()
    }
    fun initView() {
        mRxBus.toObservable(LoginSuccessEvent::class.java, Consumer {
            toast("登录成功")
            loadRootFragment(R.id.mainActivity_rl_content,MainFragment.newInstance())
            StatusBarUtil.setStatusBarVisibility(this,true)
            setTheme(R.style.AppTheme)
        }, RxLifecycleUtils.bindLifecycle(this))


    }

    override fun getSwipeBackEnable(): Boolean {
        return false
    }


}
