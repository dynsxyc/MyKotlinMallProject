package com.zhongjiang.kotlin.user.injection.module

import com.zhongjiang.kotlin.user.service.UploadService
import com.zhongjiang.kotlin.user.service.impl.UploadServiceIml
import dagger.Module
import dagger.Provides

/**
 * Created by dyn on 2018/7/16.
 */
@Module
class UploadModule {
    @Provides
    fun providesUploadService(service: UploadServiceIml): UploadService {
        return service
    }

}