package com.zhongjiang.kotlin.user.presenter

import com.zhongjiang.kotlin.base.ext.execute
import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.base.rx.BaseSubscriber
import com.zhongjiang.kotlin.user.presenter.view.RegistenerView
import com.zhongjiang.kotlin.user.service.iml.UserServiceIml

/**
 * Created by dyn on 2018/7/13.
 */
class RegistenerPresenter : BasePresenter<RegistenerView>() {
    fun  rigister(phone :String ,code : String,pwd : String){
        /*
        * 业务逻辑
        * */
//        mView.onRegisterResult(true)
        val userServer = UserServiceIml()
        userServer.register(phone,code,pwd)
                .execute(object :BaseSubscriber<Boolean>(){
                    override fun onNext(t: Boolean) {
                        mView.onRegisterResult(t)
                    }
                })
    }
}