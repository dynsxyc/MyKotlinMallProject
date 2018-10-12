package com.zhongjiang.kotlin.base.injection

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.RUNTIME
import javax.inject.Scope

/**
 * Created by dyn on 2018/7/17.
 */
@Scope
@Documented
@Retention(RUNTIME)
annotation class ActivityScope