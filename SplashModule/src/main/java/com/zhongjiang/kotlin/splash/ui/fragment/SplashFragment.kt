package com.zhongjiang.kotlin.splash.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhongjiang.kotlin.base.data.net.entity.UserInfo
import com.zhongjiang.kotlin.base.ui.fragment.BaseMvpFragment
import com.zhongjiang.kotlin.splash.R
import com.zhongjiang.kotlin.splash.presenter.SplashPresenter
import com.zhongjiang.kotlin.splash.presenter.contract.SplashContract
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * Created by dyn on 2018/7/25.
 */
class SplashFragment : BaseMvpFragment<SplashPresenter>(), SplashContract.View {
    override fun onGetUserInfo(userInfo: UserInfo) {
        mTvContent.setText(userInfo.name)
    }

    companion object {
        fun newInstance(): SplashFragment {
            val args = Bundle()
            val fragment = SplashFragment()
            fragment.setArguments(args)
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_splash, null)
        return attachToSwipeBack(view)
    }
    fun initThisView(){
        mYxImageView.setImageUserIconUrl("https://youxuan-pic.oss-cn-hangzhou.aliyuncs.com/shop/head/20181215/dd77da56-e90e-4f89-aade-f3db119ad394And.png")
        mPresenter.requestUserInfo("qingmei2")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initThisView()
    }

}