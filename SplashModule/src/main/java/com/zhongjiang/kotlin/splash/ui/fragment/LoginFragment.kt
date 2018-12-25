package com.zhongjiang.kotlin.splash.ui.fragment

import android.net.Uri
import android.os.Bundle
import com.zhongjiang.kotlin.base.ui.fragment.BaseMvpFragment
import com.zhongjiang.kotlin.splash.R
import com.zhongjiang.kotlin.splash.presenter.LoginFragmentPresenter
import com.zhongjiang.kotlin.splash.presenter.contract.LoginFragmentContract
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseMvpFragment<LoginFragmentPresenter>(), LoginFragmentContract.View {
    /**公共实现部分 start*/
    companion object {
        fun newInstance(): LoginFragment {
            val args = Bundle()
            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        mLoginFragmentVideoView.setVideoURI(Uri.parse("android.resource://" + activity?.getPackageName() + "/" + R.raw.splash_video))
        mLoginFragmentVideoView.setOnCompletionListener { mLoginFragmentVideoView.start() }
        mLoginFragmentVideoView.setOnPreparedListener{
            mp ->
            mp.setVolume(0f, 0f)
            mp.start()
        }
    }

    override fun initData() {
    }

    override fun getLayoutRes(): Int {
        return  R.layout.fragment_login
    }

    override fun getSwipeBackEnable(): Boolean {
        return false
    }

    override fun onResume() {
        super.onResume()
        mLoginFragmentVideoView.resume()
    }

    override fun onPause() {
        super.onPause()
        mLoginFragmentVideoView.pause()
    }

    override fun onBackPressedSupport(): Boolean {
        mPresenter.appExit(activity!!)
        return true
    }
    /**公共实现部分 end*/
}