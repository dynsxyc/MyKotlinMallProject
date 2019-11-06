package com.zhongjiang.youxuan.base.data.protocol

import retrofit2.http.Field

/**
 * @author dyn
 * @date on 2019/11/6  15:04
 * @packagename com.zhongjiang.youxuan.base.data.protocol
 * @fileName PageReq
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
data class PageReq(@Field("page") val page:Int,@Field("pageSize") val pageSize:Int = 15)