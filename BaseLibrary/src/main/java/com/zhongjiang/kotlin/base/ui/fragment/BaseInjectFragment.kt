package com.zhongjiang.kotlin.base.ui.fragment

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.flyco.roundview.RoundTextView
import com.zhongjiang.kotlin.base.R
import com.zhongjiang.kotlin.base.utils.StatusBarUtil
import dagger.android.support.AndroidSupportInjection
/**注入类型*/
abstract class BaseInjectFragment : BaseSupperFragment(){
    protected fun inject() {
        AndroidSupportInjection.inject(this)
        if (injectRouter())
            ARouter.getInstance().inject(this)
    }

    open fun injectRouter(): Boolean {
        return false
    }

    override fun onAttach(activity: Context) {
        inject()
        super.onAttach(activity)
    }

    override fun onResume() {
        super.onResume()
        initStatusBar()
    }

    protected open fun initStatusBar() {
        StatusBarUtil.darkMode(_mActivity)
    }

    protected var mMainToolbarRl: LinearLayout? = null
    protected var mMainToolbarRvTvBack: RoundTextView? = null
    protected var mMainToolbarViewLine: View? = null
    protected var mMainToolbarRvTvFinish: RoundTextView? = null
    protected var mMainToolbarTvTitle: TextView? = null
    protected var mMainToolbarRvTvLastRight: RoundTextView? = null
    protected var mMainToolbarRvTvRight: RoundTextView? = null
    open fun getTopView(view: View){
        mMainToolbarRl= view.findViewById(R.id.mMainToolbarRl)
        mMainToolbarRvTvBack= view.findViewById(R.id.mMainToolbarRvTvBack)
        mMainToolbarViewLine= view.findViewById(R.id.mMainToolbarViewLine)
        mMainToolbarRvTvFinish= view.findViewById(R.id.mMainToolbarRvTvFinish)
        mMainToolbarTvTitle= view.findViewById(R.id.mMainToolbarTvTitle)
        mMainToolbarRvTvLastRight= view.findViewById(R.id.mMainToolbarRvTvLastRight)
        mMainToolbarRvTvRight= view.findViewById(R.id.mMainToolbarRvTvRight)
        mMainToolbarRl?.let {
            StatusBarUtil.setPaddingSmart(_mActivity,it)
        }
    }
}