package com.zhongjiang.kotlin.user.service.impl

import com.zhongjiang.kotlin.base.ext.convert
import com.zhongjiang.kotlin.base.ext.convertBoolean
import com.zhongjiang.kotlin.user.data.protocol.UserInfo
import com.zhongjiang.kotlin.user.data.repository.UserRepository
import com.zhongjiang.kotlin.user.service.UserService
import rx.Observable
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
class UserServiceIml @Inject constructor() : UserService {


    @Inject
    lateinit var userRepository: UserRepository

    override fun login(mobile: String, verifyCode: String, pushId: String): Observable<UserInfo> {
        return userRepository.login(mobile,verifyCode,pushId).convert()
    }
    override fun register(mobile: String, verifyCode: String, pwd: String):Observable<Boolean> {
        return userRepository.register(mobile,verifyCode,pwd).convertBoolean()
    }

    override fun forgetPwd(mobile: String, verifyCode: String): Observable<Boolean> {
        return userRepository.frogetPwd(mobile,verifyCode).convertBoolean()
    }

    override fun resetPwd(mobile: String, pwd: String): Observable<Boolean> {
        return userRepository.resetPwd(mobile,pwd).convertBoolean()
    }
    override fun editUser(userIcon: String, userName: String, userGender: String, userSign: String): Observable<UserInfo> {
        return userRepository.editUser(userIcon,userName,userGender,userSign).convert()
    }
}