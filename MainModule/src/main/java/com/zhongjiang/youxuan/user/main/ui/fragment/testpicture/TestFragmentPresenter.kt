package com.zhongjiang.kotlin.splash.presenter.loginfragment

import com.zhongjiang.kotlin.splash.ui.fragment.TestPictureFragment
import com.zhongjiang.youxuan.base.ui.basemvp.BasePresenter
import com.zhongjiang.youxuan.user.main.ui.MainModel
import javax.inject.Inject

class TestFragmentPresenter @Inject constructor() : BasePresenter<TestPictureFragment, MainModel>(), TestFragmentContract.Presenter {
}