package com.zhongjiang.kotlin.user.presenter

import com.zhongjiang.kotlin.base.ext.execute
import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.base.rx.BaseSubscriber
import com.zhongjiang.kotlin.user.data.protocol.UserInfo
import com.zhongjiang.kotlin.user.presenter.view.LoginView
import com.zhongjiang.kotlin.user.service.UserService
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
class LoginPresenter @Inject constructor() : BasePresenter<LoginView>(){

    //    java中 @Named("userserver2") 这样写可以  kotlin用 @field:[Named("userserver2")] 的写法 kotlin的特性
    @Inject
    lateinit var userServer : UserService
    fun  login(phone :String ,code : String,pwd : String){
        /*
        * 业务逻辑
        * */
        if (!checkNetWork()){
            return
        }
        mView.showLoading()
        userServer.login(phone,code,pwd)
                .execute(object : BaseSubscriber<UserInfo>(mView){
                    override fun onNext(t: UserInfo) {
                        mView.onLoginResult(t)
                    }
                },lifecycleProvider)
    }
}