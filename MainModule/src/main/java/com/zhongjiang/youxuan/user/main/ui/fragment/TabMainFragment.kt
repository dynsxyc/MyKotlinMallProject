package com.zhongjiang.kotlin.splash.ui.fragment

import android.view.View
import com.zhongjiang.kotlin.splash.presenter.splashfragment.TabMainFragmentContract
import com.zhongjiang.kotlin.splash.presenter.splashfragment.TabMainFragmentPresenter
import com.zhongjiang.youxuan.base.injection.WindowScreenInfo
import com.zhongjiang.youxuan.base.ui.fragment.BaseMvpFragment
import com.zhongjiang.youxuan.user.main.R
import kotlinx.android.synthetic.main.fragment_basehome.*
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/25.
 */
class TabMainFragment : BaseMvpFragment<TabMainFragmentPresenter>(), TabMainFragmentContract.View {
    @Inject
    lateinit var screenWidth: WindowScreenInfo

    /**公共实现部分 start*/
    override fun initData() {
//        mPresenter.requestAdInfo("")
//        mPresenter.registerActivityResultEvent {
//            when (it.requestCoder) {
//                ActivityRequestCode.REQUEST_WEBSHOW_CODE.requestCode -> {
//                    mPresenter.checkSkip()
//                }
//            }
//        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_basehome
    }

    override fun initView() {
        mTestCommonHeaderTitleView.setOnViewClickListener(this)
    }

    companion object {
        fun newInstance(): TabMainFragment {
            val fragment = TabMainFragment()
            return fragment
        }
    }

    override fun getSwipeBackEnable(): Boolean {
        return false
    }
    /**公共实现部分 end*/


    override fun onRight(view: View) {
        mPresenter.startLocation(_mActivity)
    }
    override fun onLastRight(view: View) {
        start(TestPictureFragment())
    }
    /**当前业务部分 end*/

}