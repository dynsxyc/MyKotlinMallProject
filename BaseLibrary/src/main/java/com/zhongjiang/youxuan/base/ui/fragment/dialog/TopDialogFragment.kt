package com.zhongjiang.youxuan.base.ui.fragment.dialog

import android.view.Gravity

import com.gyf.barlibrary.ImmersionBar
import com.zhongjiang.youxuan.base.R

/**
 * 顶部DialogFragment
 *
 * @author geyifeng
 * @date 2017/7/28
 */
class TopDialogFragment : BaseDialogFragment() {


    override fun onStart() {
        super.onStart()
        mWindow?.setGravity(Gravity.TOP)
        mWindow?.setWindowAnimations(R.style.TopDialog)
        mWindow?.setLayout(mWidth, mHeight / 2)
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_webshow
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
                .navigationBarWithKitkatEnable(false)
                .init()
    }

    override fun onDestroy() {
        super.onDestroy()
        //        ((DialogActivity) getActivity()).getActivityImmersionBar().keyboardEnable(true).init();
    }
}
