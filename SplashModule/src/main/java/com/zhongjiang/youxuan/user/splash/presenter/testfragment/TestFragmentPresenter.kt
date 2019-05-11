package com.zhongjiang.youxuan.user.splash.presenter.loginfragment

import com.zhongjiang.youxuan.base.presenter.BasePresenter
import javax.inject.Inject

class TestFragmentPresenter @Inject constructor(view: TestFragmentContract.View, model: TestFragmentContract.Model) : BasePresenter<TestFragmentContract.View, TestFragmentContract.Model>(view, model), TestFragmentContract.Presenter {
}