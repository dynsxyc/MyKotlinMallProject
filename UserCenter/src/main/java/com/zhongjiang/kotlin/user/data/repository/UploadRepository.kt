package com.zhongjiang.kotlin.user.data.repository

import com.zhongjiang.kotlin.base.data.net.RetrofitFactory
import com.zhongjiang.kotlin.base.data.protocol.BaseResp
import com.zhongjiang.kotlin.user.data.api.UploadApi
import rx.Observable
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/16.
 * 直接访问网络
 */
open class UploadRepository @Inject constructor() {
    fun getUploadToken(): Observable<BaseResp<String>> {
       return RetrofitFactory.instance.create(UploadApi:: class.java).getUploadToken()
    }

}