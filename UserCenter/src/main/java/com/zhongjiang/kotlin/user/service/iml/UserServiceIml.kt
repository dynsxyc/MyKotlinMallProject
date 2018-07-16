package com.zhongjiang.kotlin.user.service.iml

import com.zhongjiang.kotlin.user.service.UserService
import rx.Observable

/**
 * Created by dyn on 2018/7/13.
 */
class UserServiceIml : UserService {
    override fun register(mobile: String, verifyCode: String, pwd: String):Observable<Boolean> {
        return Observable.just(true)
    }
}