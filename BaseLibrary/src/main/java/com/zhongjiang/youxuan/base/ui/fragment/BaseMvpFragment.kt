package com.zhongjiang.youxuan.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import com.zhongjiang.youxuan.base.presenter.IFragment
import com.zhongjiang.youxuan.base.presenter.IPresenter
import com.zhongjiang.youxuan.base.widgets.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
abstract class BaseMvpFragment<P : IPresenter> : BaseInjectFragment(), IFragment {
    @Inject
    protected lateinit var mPresenter: P

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(getLayoutRes(), container, false)
        initLifecycleObserver(lifecycle)
        return attachToSwipeBack(rootView)
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

    private val progressLoading by lazy {
        ProgressLoading.create(_mActivity)
    }

    @MainThread
    override fun showLoading() {
        progressLoading.showLoading()
    }
    protected fun showCancelableLoading(){
        progressLoading.showCancelableLoading()
    }

    @MainThread
    override fun onError(status:Int,text: String) {
        _mActivity.toast(text)
    }

    @MainThread
    override fun hideLoading() {
        progressLoading.hideLoading()
    }

}