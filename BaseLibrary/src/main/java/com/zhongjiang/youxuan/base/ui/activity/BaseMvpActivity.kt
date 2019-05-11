package com.zhongjiang.youxuan.base.ui.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import com.uber.autodispose.ScopeProvider
import com.zhongjiang.youxuan.base.presenter.IPresenter
import com.zhongjiang.youxuan.base.presenter.IView
import com.zhongjiang.youxuan.base.utils.RxLifecycleUtils
import com.zhongjiang.youxuan.base.widgets.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
abstract class BaseMvpActivity<P : IPresenter> : BaseInjectActivity(), IView {
    @Inject
    lateinit var mPresenter: P

    val progressLoading by lazy {
         ProgressLoading.create(this)
    }
    @MainThread
    override fun showLoading() {
        progressLoading.showLoading()
    }
    @MainThread
    override fun onError(status:Int,text: String) {
        toast(text)
    }
    @MainThread
    override fun hideLoading() {
        progressLoading.hideLoading()
    }


    override fun onStart() {
        super.onStart()
        setSwipeBackEnable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initLifecycleObserver(lifecycle)
        initView()
        initData()
    }

    @CallSuper
    @MainThread
    protected fun initLifecycleObserver(lifecycle: Lifecycle) {
        mPresenter.setLifecycleOwner(this)
        lifecycle.addObserver(mPresenter)
    }

    fun  bindLifecycle(): ScopeProvider {
        return RxLifecycleUtils.bindLifecycle(this)
    }

    /**获取当前页  layoutId资源Id*/
    @LayoutRes
    @MainThread
    abstract fun getLayoutId(): Int

    /**获取当前页数据*/
    @MainThread
    abstract fun initData()

    /**初始化当前页控件*/
    @MainThread
    abstract fun initView()


}