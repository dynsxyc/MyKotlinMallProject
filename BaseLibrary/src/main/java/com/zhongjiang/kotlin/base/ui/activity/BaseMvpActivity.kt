package com.zhongjiang.kotlin.base.ui.activity

import android.os.Bundle
import android.support.annotation.LayoutRes
import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.injection.component.ActivityComponent
import com.zhongjiang.kotlin.base.injection.component.DaggerActivityComponent
import com.zhongjiang.kotlin.base.injection.module.ActivityModule
import com.zhongjiang.kotlin.base.injection.module.LifecycleProviderModule
import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.base.presenter.view.BaseView
import com.zhongjiang.kotlin.base.widgets.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
open abstract class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity(), BaseView {
    @Inject
    lateinit var mPresenter: T

    lateinit var activityComponent: ActivityComponent
    private lateinit var progressLoading: ProgressLoading
    override fun showLoading() {
        progressLoading?.showLoading()
    }

    override fun onError(text: String) {
        toast(text)
    }

    override fun hideLoading() {
        progressLoading?.hideLoading()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()
        setContentView(getThisContentLayoutRes())
        initThisView()
        loadThisData()
        progressLoading = ProgressLoading.create(this)
    }

    abstract fun injectComponent()
    /**获取当前页  layoutId资源Id*/
    @LayoutRes abstract fun getThisContentLayoutRes():Int
    /**获取当前页数据*/
    abstract fun loadThisData()
    /**初始化当前页控件*/
    abstract fun initThisView()

    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder().lifecycleProviderModule(LifecycleProviderModule(this)).appComponent((application as BaseApplication).appComponent).activityModule(ActivityModule(this)).build()
    }
}