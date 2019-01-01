package com.zhongjiang.kotlin.base.ui.fragment

import android.app.Activity
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.zhongjiang.kotlin.base.ui.activity.BaseSupperFragment
import dagger.android.support.AndroidSupportInjection
/**注入类型*/
abstract class BaseInjectFragment : BaseSupperFragment(){
    protected fun inject() {
        AndroidSupportInjection.inject(this)
        if (injectRouter())
            ARouter.getInstance().inject(this)
    }

    protected fun injectRouter(): Boolean {
        return false
    }

    override fun onAttach(activity: Context) {
        inject()
        super.onAttach(activity)
    }
}