package com.zhongjiang.kotlin.base.data.protocol

/**
 * Created by dyn on 2018/7/16.
 */
class BaseResp<out T>(val status: Int, val showMessage: String, val data: T)