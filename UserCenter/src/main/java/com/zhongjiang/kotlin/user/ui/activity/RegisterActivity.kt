package com.zhongjiang.kotlin.user.ui.activity

import android.os.Bundle
import com.zhongjiang.kotlin.base.ui.activity.BaseMvpActivity
import com.zhongjiang.kotlin.user.R
import com.zhongjiang.kotlin.user.presenter.RegistenerPresenter
import com.zhongjiang.kotlin.user.presenter.view.RegistenerView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.debug
import org.jetbrains.anko.dip
import org.jetbrains.anko.px2dip
import org.jetbrains.anko.toast

class RegisterActivity : BaseMvpActivity<RegistenerPresenter>(),RegistenerView {
    override fun onRegisterResult(result: Boolean) {
        toast("注册成功")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mPresenter = RegistenerPresenter()
        mPresenter.mView = this
        register_btn.setOnClickListener {
//            Toast.makeText(this,"注册",Toast.LENGTH_SHORT).show();
//            startActivity(intentFor<TestActivity>("id" to 5))
            debug("dp = "+dip(5f))
            debug("px2dp = "+px2dip(5))
            mPresenter.rigister("","","")
        }
    }

}
