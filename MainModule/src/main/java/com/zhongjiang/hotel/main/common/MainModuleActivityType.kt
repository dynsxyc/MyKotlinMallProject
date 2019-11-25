package com.zhongjiang.hotel.main.common

import com.blankj.utilcode.util.StringUtils

/**
 * @date on 2019/5/8 11:13
 * @packagename com.zhongjiang.youxuan.user.main.common
 * @author dyn
 * @fileName MainModuleActivityType
 * @org com.zhongjiang.youxuan
 * @describe 模块采用单页面 多fragment 构建
 * @email 583454199@qq.com
 **/
enum class MainModuleActivityType  {
    MAIN_FRAGMENT;
    companion object{
        fun nameToType(name:String?):MainModuleActivityType?{
            return values().firstOrNull{
                StringUtils.equals(name, it.name)
            }
        }
    }
}