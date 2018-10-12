package com.zhongjiang.kotlin.user.presenter.view

import com.zhongjiang.kotlin.base.presenter.view.BaseView
import com.zhongjiang.kotlin.user.data.protocol.UserInfo

/**
 * Created by dyn on 2018/7/13.
 */
interface LoginView : BaseView {
    fun onLoginResult(result : UserInfo)
}