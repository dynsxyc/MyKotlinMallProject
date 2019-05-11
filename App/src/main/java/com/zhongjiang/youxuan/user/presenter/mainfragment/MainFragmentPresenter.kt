package com.zhongjiang.youxuan.user.presenter.mainfragment

import com.zhongjiang.youxuan.base.presenter.BasePresenter
import javax.inject.Inject

class MainFragmentPresenter @Inject constructor(view: MainFragmentContract.View, model: MainFragmentContract.Model) : BasePresenter<MainFragmentContract.View, MainFragmentContract.Model>(view,model) , MainFragmentContract.Presenter{

}