package com.zhongjiang.kotlin.mall.presenter

import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.mall.presenter.contract.MainActivityContract
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(view:MainActivityContract.View, model:MainActivityContract.Model) : BasePresenter<MainActivityContract.View,MainActivityContract.Model>(view,model) , MainActivityContract.Presenter{

}