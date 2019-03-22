package com.zhongjiang.kotlin.mall.ui.fragment

import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.rxbinding2.view.RxView
import com.zhongjiang.kotlin.base.ext.shieldDoubleClick
import com.zhongjiang.kotlin.mall.R
import com.zhongjiang.kotlin.mall.presenter.mainfragment.MainFragmentContract
import com.zhongjiang.kotlin.mall.ui.activity.TestStatusBarActivity
import com.zhongjiang.kotlin.provider.common.CommonUtils
import com.zhongjiang.kotlin.provider.router.NavigationUtil
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject
import javax.inject.Singleton

class MainFragment : BaseMainFragment<MainFragmentContract.Presenter>(),MainFragmentContract.View {

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
            NavigationUtil.navigationToWebShow("https://www.baidu.com")
            commonUtils.removeUserInfo()
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
}