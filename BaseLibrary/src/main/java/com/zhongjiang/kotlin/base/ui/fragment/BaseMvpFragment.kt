package com.zhongjiang.kotlin.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import com.zhongjiang.kotlin.base.presenter.IFragment
import com.zhongjiang.kotlin.base.presenter.IPresenter
import com.zhongjiang.kotlin.base.widgets.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
abstract class BaseMvpFragment<P: IPresenter> : BaseInjectFragment() , IFragment {
    @Inject
    protected lateinit var mPresenter : P

    protected lateinit var rootView: View

    override fun onStart() {
        super.onStart()
        setSwipeBackEnable(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = LayoutInflater.from(context).inflate(getLayoutRes(), container, false)
        initLifecycleObserver(lifecycle)
        return if (getSwipeBackEnable()) rootView else attachToSwipeBack(rootView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    protected abstract fun initView()

    protected abstract fun initData()

    protected abstract fun getLayoutRes(): Int

    @CallSuper
    @MainThread
    protected fun initLifecycleObserver(lifecycle: Lifecycle) {
        mPresenter.setLifecycleOwner(this)
        lifecycle.addObserver(mPresenter)
    }

    val progressLoading by lazy {
        ProgressLoading.create(activity!!)
    }
    @MainThread
    override fun showLoading() {
        progressLoading.showLoading()
    }
    @MainThread
    override fun onError(text: String) {
        activity!!.toast(text)
    }
    @MainThread
    override fun hideLoading() {
        progressLoading.hideLoading()
    }

}