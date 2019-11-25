package com.zhongjiang.hotel.main.ui.activity

import com.zhongjiang.hotel.main.ui.fragment.tabmain.TabMainFragment
import com.zhongjiang.hotel.main.ui.fragment.testpicture.TestPictureFragment
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import com.zhongjiang.hotel.base.ui.activity.BaseInjectActivity
import com.zhongjiang.hotel.base.ui.fragment.BaseSupperFragment
import com.zhongjiang.hotel.main.R
import com.zhongjiang.hotel.main.common.MainModuleActivityType
import com.zhongjiang.hotel.provider.router.NavigationConstant
import com.zhongjiang.hotel.provider.router.RouterPath

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
    var type: String = ""

    @Autowired(name = NavigationConstant.NAVIGATION_DATA_STRING_MODULE_MAP)
    @JvmField
    var mapParams: Map<String, String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var supportFragment: BaseSupperFragment = when (MainModuleActivityType.nameToType(type)) {
            null -> TestPictureFragment()
            MainModuleActivityType.MAIN_FRAGMENT -> TabMainFragment()
        }
        var bundle = Bundle()
        mapParams?.let { it ->
            it.forEach { item ->
                bundle.putString(item.key, item.value)
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