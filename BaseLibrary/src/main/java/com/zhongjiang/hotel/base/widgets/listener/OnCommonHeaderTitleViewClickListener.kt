package com.zhongjiang.hotel.base.widgets.listener

import android.view.View

/**
 * @date on 2019/5/10 14:48
 * @packagename com.zhongjiang.youxuan.base.widgets.stateListener
 * @author dyn
 * @fileName OnCommonHeaderTitleViewClickListener
 * @org com.zhongjiang.youxuan
 * @describe 全局的标题事件响应
 * @email 583454199@qq.com
 **/
interface OnCommonHeaderTitleViewClickListener {
    fun onBack(view: View)
    fun onFinish(view: View)
    fun onRight(view: View)
    fun onLastRight(view: View)
}