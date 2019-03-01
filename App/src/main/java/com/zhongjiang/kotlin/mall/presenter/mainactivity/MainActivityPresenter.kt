package com.zhongjiang.kotlin.mall.presenter.mainactivity

import com.zhongjiang.kotlin.base.presenter.BasePresenter
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(view: MainActivityContract.View, model: MainActivityContract.Model) : BasePresenter<MainActivityContract.View, MainActivityContract.Model>(view,model) , MainActivityContract.Presenter{

}