package com.zhongjiang.youxuan.base.utils

import com.orhanobut.logger.Logger

/**
 * @author dyn
 * @date on 2019/11/6  16:38
 * @packagename com.zhongjiang.youxuan.base.utils
 * @fileName Ulooger
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
object Ulooger {
    fun i(any:String){
        Logger.i(any)
    }
    fun d(){
        com.ihsanbal.logging.Logger { i, s, s2 ->  }
    }
}