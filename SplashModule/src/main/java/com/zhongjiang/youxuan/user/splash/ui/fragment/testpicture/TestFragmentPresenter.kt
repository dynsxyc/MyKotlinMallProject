package com.zhongjiang.youxuan.user.splash.presenter.loginfragment

import com.zhongjiang.youxuan.base.ui.basemvp.BasePresenter
import com.zhongjiang.youxuan.user.splash.ui.SplashDataModel
import com.zhongjiang.youxuan.user.splash.ui.fragment.testpicture.TestPictureFragment
import javax.inject.Inject

class TestFragmentPresenter @Inject constructor() : BasePresenter<TestPictureFragment, SplashDataModel>(), TestFragmentContract.Presenter {
}