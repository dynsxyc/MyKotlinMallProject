package com.zhongjiang.kotlin.mall.ui.activity

import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding2.view.RxView
import com.zhongjiang.kotlin.base.ext.shieldDoubleClick
import com.zhongjiang.kotlin.base.ui.activity.BaseInjectActivity
import com.zhongjiang.kotlin.mall.R
import kotlinx.android.synthetic.main.activity_test_status_bar.*

class TestStatusBarActivity : BaseInjectActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_status_bar)
        getTopView()
        RxView.clicks(TESTbT).shieldDoubleClick {
            startActivity(Intent(this@TestStatusBarActivity,TestStatusBarActivity::class.java))
        }
    }
}
