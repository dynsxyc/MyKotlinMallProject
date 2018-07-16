package com.zhongjiang.kotlin.user.service

import rx.Observable

/**
 * Created by dyn on 2018/7/13.
 */
interface UserService {
    fun register(mobile:String ,verifyCode :String ,pwd:String):Observable<Boolean>
}