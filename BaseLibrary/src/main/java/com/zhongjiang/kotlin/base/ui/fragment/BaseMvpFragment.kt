package com.zhongjiang.kotlin.base.ui.fragment

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.MainThread
import android.view.View
import com.zhongjiang.kotlin.base.presenter.IFragment
import com.zhongjiang.kotlin.base.presenter.IPresenter
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
abstract class BaseMvpFragment<P: IPresenter> : BaseFragment() , IFragment {
    @Inject
    protected lateinit var mPresenter : P

    override fun showLoading() {
    }

    override fun onError(text:String) {
        toast(text)
    }

    override fun hideLoading() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLifecycleObserver(lifecycle)
    }
    @CallSuper
    @MainThread
    protected fun initLifecycleObserver(lifecycle: Lifecycle) {
        mPresenter.setLifecycleOwner(this)
        lifecycle.addObserver(mPresenter)
    }


}