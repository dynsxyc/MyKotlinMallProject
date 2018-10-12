package com.zhongjiang.kotlin.base.ui.fragment

import android.os.Bundle
import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.injection.component.ActivityComponent
import com.zhongjiang.kotlin.base.injection.component.DaggerActivityComponent
import com.zhongjiang.kotlin.base.injection.module.ActivityModule
import com.zhongjiang.kotlin.base.injection.module.LifecycleProviderModule
import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.base.presenter.view.BaseView
import com.zhongjiang.kotlin.base.ui.activity.BaseFragment
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
open abstract class BaseMvpFragment<T: BasePresenter<*>> : BaseFragment() , BaseView {
    @Inject
    lateinit var mPresenter : T

    lateinit var activityComponent : ActivityComponent
    override fun showLoading() {
    }

    override fun onError(text:String) {
        toast(text)
    }

    override fun hideLoading() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()
    }

    abstract fun injectComponent()

    private fun initActivityInjection() {
        activityComponent =   DaggerActivityComponent.builder().lifecycleProviderModule(LifecycleProviderModule(this))
                .appComponent((activity!!.application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this.activity!!)).build()
    }


}