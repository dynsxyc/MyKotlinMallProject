package com.zhongjiang.kotlin.user.data.repository

import com.zhongjiang.kotlin.base.data.net.RetrofitFactory
import com.zhongjiang.kotlin.base.data.protocol.BaseResp
import com.zhongjiang.kotlin.user.data.api.UserApi
import com.zhongjiang.kotlin.user.data.protocol.RegisterReq
import com.zhongjiang.kotlin.user.data.protocol.UserInfo
import rx.Observable
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/16.
 * 直接访问网络
 */
open class UserRepository @Inject constructor() {
    fun register(mobile:String ,verifyCode :String ,pwd:String): Observable<BaseResp<String>> {
       return RetrofitFactory.instance.create(UserApi :: class.java).register(mobile,pwd,verifyCode)
    }

    fun login(mobile:String ,verifyCode :String ,pwd:String): Observable<BaseResp<UserInfo>> {
        return RetrofitFactory.instance.create(UserApi :: class.java).login(RegisterReq( mobile,pwd,verifyCode))
    }

    fun frogetPwd(mobile:String ,verifyCode :String ): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi :: class.java).forgetPwd(mobile,verifyCode)
    }

    fun resetPwd(mobile:String ,pwd:String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi :: class.java).resetPwd(mobile,pwd)
    }
    fun editUser(userIcon: String, userName: String, userGender: String, userSign: String): Observable<BaseResp<UserInfo>> {
        return RetrofitFactory.instance.create(UserApi :: class.java).editUser(userIcon,userName,userGender,userSign)
    }
}