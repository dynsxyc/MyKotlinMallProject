package com.zhongjiang.kotlin.user.presenter

import com.zhongjiang.kotlin.base.ext.execute
import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.base.rx.BaseSubscriber
import com.zhongjiang.kotlin.user.data.protocol.UserInfo
import com.zhongjiang.kotlin.user.presenter.view.RegistenerView
import com.zhongjiang.kotlin.user.service.UserService
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
class RegistenerPresenter @Inject constructor() : BasePresenter<RegistenerView>(){

    //    java中 @Named("userserver2") 这样写可以  kotlin用 @field:[Named("userserver2")] 的写法 kotlin的特性
    @Inject
    lateinit var userServer : UserService
    fun  rigister(phone :String ,code : String,pwd : String){
        /*
        * 业务逻辑
        * */
        if (!checkNetWork()){
            return
        }
        mView.showLoading()
        userServer.register(phone,code,pwd)
                .execute(object :BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        if (t)
                        mView.onRegisterResult("注册成功")
                    }
                },lifecycleProvider)
//        userServer.register(phone,code,pwd).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Subscriber<Boolean>(){
//            override fun onError(e: Throwable?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onCompleted() {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onNext(t: Boolean?) {
//                mView.onRegisterResult("注册成功")
//            }
//
//        })
    }
    fun  login(phone :String ,code : String,pwd : String){
        /*
        * 业务逻辑
        * */
        if (!checkNetWork()){
            return
        }
        mView.showLoading()
        userServer.login(phone,code,pwd)
                .execute(object :BaseSubscriber<UserInfo>(mView){
                    override fun onNext(t: UserInfo) {
//                        mView.onRegisterResult("注册成功")
                    }
                },lifecycleProvider)
//        userServer.register(phone,code,pwd).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Subscriber<Boolean>(){
//            override fun onError(e: Throwable?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onCompleted() {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onNext(t: Boolean?) {
//                mView.onRegisterResult("注册成功")
//            }
//
//        })
    }
}