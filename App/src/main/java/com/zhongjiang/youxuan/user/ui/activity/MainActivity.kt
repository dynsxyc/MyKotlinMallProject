package com.zhongjiang.youxuan.user.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import com.zhongjiang.youxuan.base.busevent.LoginSuccessEvent
import com.zhongjiang.youxuan.base.ui.activity.BaseInjectActivity
import com.zhongjiang.youxuan.base.utils.RxLifecycleUtils
import com.zhongjiang.youxuan.user.R
import com.zhongjiang.youxuan.user.ui.fragment.MainFragment
import com.zhongjiang.youxuan.provider.common.CommonUtils
import com.zhongjiang.youxuan.provider.router.NavigationUtil
import com.zhongjiang.youxuan.provider.router.RouterPath
import io.reactivex.functions.Consumer
import javax.inject.Inject
import javax.inject.Singleton

@Route(path = RouterPath.MainCenter.PATH_MAIN)
class MainActivity : BaseInjectActivity(){
    @Inject
    @Singleton
    lateinit var commonUtils: CommonUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationUtil.navigationToLogin(false);
        setContentView(R.layout.activity_single_fragment)
        initView()
    }
    private fun initView() {
        mRxBus.toObservable(LoginSuccessEvent::class.java, Consumer {
            loadRootFragment(R.id.mActivitySingleFragmentContent,MainFragment.newInstance())
        }, RxLifecycleUtils.bindBusLifecycle(this))
    }

    override fun getSwipeBackEnable(): Boolean {
        return false
    }
    override fun onBackPressedSupport() {
        if (findFragment(MainFragment::class.java) != null && findFragment(MainFragment::class.java).isBackEnable){
            commonUtils.appExit(this)
        }
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init()
    }

}
