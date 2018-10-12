package com.zhongjiang.kotlin.user.service.impl

import com.zhongjiang.kotlin.base.ext.convert
import com.zhongjiang.kotlin.user.data.repository.UploadRepository
import com.zhongjiang.kotlin.user.service.UploadService
import rx.Observable
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
class UploadServiceIml @Inject constructor() : UploadService {
    override fun getUploadToken(): Observable<String> {
        return uploadRepository.getUploadToken().convert()
    }

    @Inject
    lateinit var uploadRepository: UploadRepository

}