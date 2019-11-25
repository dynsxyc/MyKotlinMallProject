package com.zhongjiang.hotel.main.ui.fragment.testpicture

import com.zhongjiang.hotel.main.ui.MainModel
import com.zhongjiang.hotel.base.ui.basemvp.BasePresenter
import javax.inject.Inject

class TestFragmentPresenter @Inject constructor(mainModule: MainModel) : BasePresenter<TestPictureFragment>(mainModule), TestFragmentContract.Presenter {
}