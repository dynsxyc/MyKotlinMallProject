package com.zhongjiang.kotlin.user.injection.component

import com.zhongjiang.kotlin.base.injection.PreCommponentScope
import com.zhongjiang.kotlin.base.injection.component.BaseActivityComponent
import com.zhongjiang.kotlin.user.injection.module.UploadModule
import com.zhongjiang.kotlin.user.injection.module.UserModule
import com.zhongjiang.kotlin.user.ui.activity.*
import dagger.Component

/**
 * Created by dyn on 2018/7/16.
 */
@Component(modules = arrayOf(UserModule::class,UploadModule::class) ,dependencies = arrayOf(BaseActivityComponent::class))
@PreCommponentScope
interface UserComponent {
    fun inject(activity:RegisterActivity)
    fun inject(activity:LoginActivity)
    fun inject(activity:ForgetPwdActivity)
    fun inject(activity:ResetPwdActivity)
    fun inject(activity:UserInfoActivity)
}