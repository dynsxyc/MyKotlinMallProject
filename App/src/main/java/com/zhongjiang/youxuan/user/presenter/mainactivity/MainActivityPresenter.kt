package com.zhongjiang.youxuan.user.presenter.mainactivity

import com.zhongjiang.youxuan.base.presenter.BasePresenter
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(view: MainActivityContract.View, model: MainActivityContract.Model) : BasePresenter<MainActivityContract.View, MainActivityContract.Model>(view,model) , MainActivityContract.Presenter{

}