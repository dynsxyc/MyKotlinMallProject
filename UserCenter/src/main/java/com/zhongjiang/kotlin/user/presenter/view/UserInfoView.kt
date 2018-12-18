package com.zhongjiang.kotlin.user.presenter.view

import com.zhongjiang.kotlin.base.presenter.IView
import com.zhongjiang.kotlin.user.data.protocol.UserInfo

/**
 * Created by dyn on 2018/7/13.
 */
interface UserInfoView : IView {
    fun onGetUploadTokenResult(result:String)
    fun onEditUserResult(result:UserInfo)
}