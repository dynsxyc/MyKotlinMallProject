package com.zhongjiang.kotlin.user.presenter

import com.zhongjiang.kotlin.base.ext.execute
import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.base.rx.BaseSubscriber
import com.zhongjiang.kotlin.user.presenter.view.ResetPwdView
import com.zhongjiang.kotlin.user.service.UserService
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
class ResetPwdPresenter @Inject constructor() : BasePresenter<ResetPwdView>(){

    //    java中 @Named("userserver2") 这样写可以  kotlin用 @field:[Named("userserver2")] 的写法 kotlin的特性
    @Inject
    lateinit var userServer : UserService
    fun  resetPwd(phone :String ,pwd : String){
        /*
        * 业务逻辑
        * */
        if (!checkNetWork()){
            return
        }
        mView.showLoading()
        userServer.resetPwd(phone,pwd)
                .execute(object : BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        if (t)
                        mView.onResetPwdResult("重置成功")
                    }
                },lifecycleProvider)
    }
}