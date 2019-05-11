package com.zhongjiang.youxuan.user.main.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import com.zhongjiang.kotlin.splash.ui.fragment.SplashFragment
import com.zhongjiang.youxuan.base.ui.activity.BaseInjectActivity
import com.zhongjiang.youxuan.provider.common.CommonUtils
import com.zhongjiang.youxuan.provider.router.NavigationConstant
import com.zhongjiang.youxuan.provider.router.NavigationUtil
import com.zhongjiang.youxuan.provider.router.RouterPath
import com.zhongjiang.youxuan.user.main.R
import com.zhongjiang.youxuan.user.main.common.MainModuleSingleActivityEntity
import com.zhongjiang.youxuan.user.main.common.MainModuleSingleActivityType
import javax.inject.Inject
import javax.inject.Singleton
/**
 * @date on 2019/05/08 09:24
 * @packagename
 * @author dyn
 * @org com.zhongjiang.youxuan
 * @describe 模块入口
 * @email 583454199@qq.com
 **/
@Route(path = RouterPath.MainModuleCenter.PATH_MAIN_MODULE_ENTRANCE)
class MainSingleFragmentActivity : BaseInjectActivity() {
    @Autowired(name = NavigationConstant.NAVIGATION_DATA_PARCELABLE_CONTENT)
    @JvmField
    var intentData: MainModuleSingleActivityEntity? = null

    @Inject
    @Singleton
    lateinit var commonUtils: CommonUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)
        if (intentData == null) {
            NavigationUtil.navigationToMainModultEntrance(MainModuleSingleActivityEntity(MainModuleSingleActivityType.TEST_MAIN_FRAGMENT,null,null))
            finish()
            return
        }
        intentData?.let {
            when(it.type){
                MainModuleSingleActivityType.TEST_MAIN_FRAGMENT->
                    loadRootFragment(R.id.mActivitySingleFragmentContent, SplashFragment.newInstance())
            }
        }

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
}