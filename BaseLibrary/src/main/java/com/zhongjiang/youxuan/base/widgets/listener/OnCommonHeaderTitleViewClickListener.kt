package com.zhongjiang.youxuan.base.widgets.listener

import android.view.View

/**
 * @date on 2019/5/10 14:48
 * @packagename com.zhongjiang.youxuan.base.widgets.listener
 * @author dyn
 * @fileName OnCommonHeaderTitleViewClickListener
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
interface OnCommonHeaderTitleViewClickListener {
    fun onBack(view: View)
    fun onFinish(view: View)
    fun onRight(view: View)
    fun onLastRight(view: View)
}