package com.zhongjiang.youxuan.user.main.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import com.zhongjiang.kotlin.splash.ui.fragment.TabMainFragment
import com.zhongjiang.kotlin.splash.ui.fragment.TestPictureFragment
import com.zhongjiang.youxuan.base.ui.activity.BaseInjectActivity
import com.zhongjiang.youxuan.base.ui.fragment.BaseSupperFragment
import com.zhongjiang.youxuan.provider.router.NavigationConstant
import com.zhongjiang.youxuan.provider.router.RouterPath
import com.zhongjiang.youxuan.user.main.R
import com.zhongjiang.youxuan.user.main.common.MainModuleActivityType

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
    override fun getLayoutId(): Int {
        return R.layout.activity_single_fragment
    }

    @Autowired(name = NavigationConstant.NAVIGATION_DATA_STRING_MODULE_TYPE)
    @JvmField
    var type: String? = null

    @Autowired(name = NavigationConstant.NAVIGATION_DATA_STRING_MODULE_MAP)
    @JvmField
    var mapParams: Map<String,String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var supportFragment:BaseSupperFragment = when(MainModuleActivityType.nameToType(type)){
            null -> TestPictureFragment()
            MainModuleActivityType.MAIN_FRAGMENT -> TabMainFragment()
        }
        var  bundle = Bundle()
        mapParams?.let { it ->
            it.forEach {item->
                bundle.putString(item.key,item.value)
            }
        }
        supportFragment.arguments = bundle
        loadRootFragment(R.id.mActivitySingleFragmentContent, supportFragment)
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
        super.onBackPressedSupport()
    }
}