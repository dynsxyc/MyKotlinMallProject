package com.zhongjiang.kotlin.user.injection.module

import com.zhongjiang.kotlin.user.service.UserService
import com.zhongjiang.kotlin.user.service.impl.UserServiceIml
import dagger.Module
import dagger.Provides

/**
 * Created by dyn on 2018/7/16.
 */
@Module
class UserModule {
    @Provides
    fun providesUserService(service: UserServiceIml):UserService{
        return service
    }

}