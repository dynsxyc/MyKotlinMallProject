package com.zhongjiang.kotlin.user.service

import rx.Observable

/**
 * Created by dyn on 2018/7/13.
 */
interface UploadService {
    fun getUploadToken(): Observable<String>
}