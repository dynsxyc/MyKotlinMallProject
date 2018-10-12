package com.zhongjiang.kotlin.user.presenter

import com.zhongjiang.kotlin.base.ext.execute
import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.base.rx.BaseSubscriber
import com.zhongjiang.kotlin.user.data.protocol.UserInfo
import com.zhongjiang.kotlin.user.presenter.view.UserInfoView
import com.zhongjiang.kotlin.user.service.UploadService
import com.zhongjiang.kotlin.user.service.UserService
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
class UserInfoPresenter @Inject constructor() : BasePresenter<UserInfoView>(){

    //    java中 @Named("userserver2") 这样写可以  kotlin用 @field:[Named("userserver2")] 的写法 kotlin的特性
    @Inject
    lateinit var userServer : UserService
    @Inject
    lateinit var uploadService : UploadService
    /*
    * 获取七牛云上传凭证
    * */
    fun  getUploadToken(){
        /*
        * 业务逻辑
        * */
        if (!checkNetWork()){
            return
        }
        mView.showLoading()
        uploadService.getUploadToken()
                .execute(object : BaseSubscriber<String>(mView){
                    override fun onNext(t: String) {
                        mView.onGetUploadTokenResult(t)
                    }
                },lifecycleProvider)
    }
    /*
        编辑用户资料
     */
    fun editUser(userIcon:String,userName:String,userGender:String,userSign:String){
        if (!checkNetWork())
            return

        mView.showLoading()
        userServer.editUser(userIcon,userName,userGender,userSign).execute(object :BaseSubscriber<UserInfo>(mView){
            override fun onNext(t: UserInfo) {
                mView.onEditUserResult(t)
            }
        },lifecycleProvider)
    }

}