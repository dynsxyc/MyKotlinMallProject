package com.zhongjiang.youxuan.user.ui.fragment

import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gyf.barlibrary.ImmersionBar
import com.jakewharton.rxbinding2.view.RxView
import com.zhongjiang.youxuan.base.ext.shieldDoubleClick
import com.zhongjiang.youxuan.user.R
import com.zhongjiang.youxuan.user.presenter.mainfragment.MainFragmentContract
import com.zhongjiang.youxuan.user.ui.activity.TestStatusBarActivity
import com.zhongjiang.youxuan.provider.common.CommonUtils
import com.zhongjiang.youxuan.provider.router.NavigationUtil
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject
import javax.inject.Singleton

class MainFragment : BaseMainFragment<MainFragmentContract.Presenter>(),MainFragmentContract.View {

    val isBackEnable: Boolean = true

    @Inject
    @Singleton
    lateinit var commonUtils: CommonUtils

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mainFragmentMessage.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                mainFragmentMessage.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                mainFragmentMessage.setText(R.string.title_notifications)
                showCancelableLoading()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun initView() {
        view?.let { getTopView(it) }
        mainFragmentNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        RxView.clicks(mainFragmentMessage).shieldDoubleClick {
            startActivity(Intent(_mActivity,TestStatusBarActivity::class.java))
        }
        RxView.clicks(mainFragmentMessageGoWeb).shieldDoubleClick {
            commonUtils.removeUserInfo()
            NavigationUtil.navigationToLogin(true)
        }
        mMainToolbarTvTitle?.let {
            it.text = "首页"
        }
    }

    override fun initData() {
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main
    }

    companion object {
        fun newInstance():MainFragment{
            return MainFragment()
        }
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).init()
    }
}