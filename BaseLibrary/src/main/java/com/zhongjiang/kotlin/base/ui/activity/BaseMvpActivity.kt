package com.zhongjiang.kotlin.base.ui.activity

import android.annotation.SuppressLint
import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.annotation.MainThread
import com.zhongjiang.kotlin.base.presenter.IPresenter
import com.zhongjiang.kotlin.base.presenter.IView
import com.zhongjiang.kotlin.base.widgets.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
abstract class BaseMvpActivity<P :IPresenter> : BaseActivity(), IView {
    @Inject
    lateinit var mPresenter: P

    private lateinit var progressLoading: ProgressLoading
    override fun showLoading() {
        progressLoading.showLoading()
    }

    override fun onError(text: String) {
        toast(text)
    }

    override fun hideLoading() {
        progressLoading.hideLoading()
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentLayoutRes())
        initLifecycleObserver(lifecycle)
        initThisView()
        loadThisData()
        progressLoading = ProgressLoading.create(this)
    }

    @CallSuper
    @MainThread
    protected fun initLifecycleObserver(lifecycle: Lifecycle) {
        mPresenter.setLifecycleOwner(this)
        lifecycle.addObserver(mPresenter)
    }

    /**获取当前页  layoutId资源Id*/
    @LayoutRes abstract fun getContentLayoutRes():Int
    /**获取当前页数据*/
    abstract fun loadThisData()
    /**初始化当前页控件*/
    abstract fun initThisView()


}