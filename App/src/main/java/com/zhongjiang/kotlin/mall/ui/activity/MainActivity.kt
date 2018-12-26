package com.zhongjiang.kotlin.mall.ui.activity

import androidx.appcompat.app.AppCompatActivity
import com.zhongjiang.kotlin.mall.R
import com.zhongjiang.kotlin.base.ui.activity.BaseMvpActivity
import com.zhongjiang.kotlin.mall.presenter.MainActivityPresenter
import com.zhongjiang.kotlin.mall.presenter.contract.MainActivityContract

class MainActivity : BaseMvpActivity<MainActivityPresenter>() , MainActivityContract.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }

    override fun initView() {
    }

}
