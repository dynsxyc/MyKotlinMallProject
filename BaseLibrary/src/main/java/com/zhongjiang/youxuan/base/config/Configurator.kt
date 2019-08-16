package com.zhongjiang.youxuan.base.config

import java.util.*

/**
 * @date on 2019/7/3 16:28
 * @packagename com.zhongjiang.youxuan.base.config
 * @author Administrator
 * @fileName Configurator
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
class Configurator {
    private constructor()
    companion object{
        val CONFIGS = WeakHashMap<String,Any>()

    }
}