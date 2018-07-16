package com.zhongjiang.kotlin.base.ui.activity

import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.base.presenter.view.BaseView

/**
 * Created by dyn on 2018/7/13.
 */
open class BaseMvpActivity<T:BasePresenter<*>> : BaseActivity() ,BaseView{
    lateinit var mPresenter : T
    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}