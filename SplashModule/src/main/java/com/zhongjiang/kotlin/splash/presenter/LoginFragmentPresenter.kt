package com.zhongjiang.kotlin.splash.presenter

import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.splash.presenter.contract.LoginFragmentContract
import javax.inject.Inject

class LoginFragmentPresenter  @Inject constructor(view: LoginFragmentContract.View, model: LoginFragmentContract.Model) : BasePresenter<LoginFragmentContract.View, LoginFragmentContract.Model>(view, model), LoginFragmentContract.Presenter {
}