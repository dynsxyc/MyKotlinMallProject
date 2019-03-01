package com.zhongjiang.kotlin.mall.presenter.mainfragment

import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.mall.presenter.mainfragment.MainFragmentContract
import javax.inject.Inject

class MainFragmentPresenter @Inject constructor(view: MainFragmentContract.View, model: MainFragmentContract.Model) : BasePresenter<MainFragmentContract.View, MainFragmentContract.Model>(view,model) , MainFragmentContract.Presenter{

}