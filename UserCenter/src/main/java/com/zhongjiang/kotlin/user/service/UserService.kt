package com.zhongjiang.kotlin.user.service

import com.zhongjiang.kotlin.user.data.protocol.UserInfo
import rx.Observable

/**
 * Created by dyn on 2018/7/13.
 */
interface UserService {
    fun register(mobile:String ,verifyCode :String ,pwd:String):Observable<Boolean>
    fun forgetPwd(mobile:String ,verifyCode :String ):Observable<Boolean>
    fun resetPwd(mobile:String ,pwd:String):Observable<Boolean>
    fun login(mobile:String ,verifyCode :String ,pushId:String):Observable<UserInfo>
    fun editUser(userIcon:String,userName:String,userGender:String,userSign:String):Observable<UserInfo>
}