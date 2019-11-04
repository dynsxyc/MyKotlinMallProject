package com.zhongjiang.kotlin.splash.presenter.loginfragment

import com.zhongjiang.kotlin.splash.ui.fragment.TestPictureFragment
import com.zhongjiang.youxuan.base.presenter.BasePresenter
import javax.inject.Inject

class TestFragmentPresenter @Inject constructor(view: TestFragmentContract.View, model: TestFragmentContract.Model) : BasePresenter<TestPictureFragment, TestFragmentContract.Model>(), TestFragmentContract.Presenter {
}