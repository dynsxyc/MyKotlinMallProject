package com.zhongjiang.kotlin.base.ui.activity

import com.zhongjiang.kotlin.base.presenter.IActivity

open class BaseActivity : BaseInjectActivity(), IActivity {
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError(text: String) {
    }
}