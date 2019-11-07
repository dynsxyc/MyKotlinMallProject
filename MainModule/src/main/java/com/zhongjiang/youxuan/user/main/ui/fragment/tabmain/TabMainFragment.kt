package com.zhongjiang.kotlin.splash.ui.fragment

import android.os.Bundle
import android.view.View
import com.zhongjiang.kotlin.splash.presenter.splashfragment.TabMainFragmentContract
import com.zhongjiang.kotlin.splash.presenter.splashfragment.TabMainFragmentPresenter
import com.zhongjiang.youxuan.base.injection.WindowScreenInfo
import com.zhongjiang.youxuan.base.ui.fragment.BaseMvpFragment
import com.zhongjiang.youxuan.base.utils.ULogger
import com.zhongjiang.youxuan.user.main.R
import com.zhongjiang.youxuan.user.main.ui.MainModel
import kotlinx.android.synthetic.main.fragment_basehome.*
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/25.
 */
class TabMainFragment : BaseMvpFragment<TabMainFragmentPresenter,MainModel>(), TabMainFragmentContract.View {
    @Inject
    lateinit var screenWidth: WindowScreenInfo

    /**公共实现部分 start*/
    override fun initData() {
        arguments?.let {
            ULogger.i(arguments)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_basehome
    }

    override fun initView() {
        mTestCommonHeaderTitleView.setOnViewClickListener(this)
    }

    override fun getSwipeBackEnable(): Boolean {
        return false
    }
    /**公共实现部分 end*/


    override fun onRight(view: View) {
        ULogger.i(screenWidth)
        ULogger.w("wwwwwwwwwwwwwwwwwwwww")
        ULogger.d(arrayListOf("d1","d1","d1","d1","d1"))
        ULogger.e(arrayOf("eeeeeeeeeeeeeeeee"))
        ULogger.v(arrayListOf("vvvvvvvvvvvvvvvvvvvvvvvvv","vvv"))
        ULogger.v(Exception())
        ULogger.v(mapOf(("a" to "A"),("c" to "C")))
        var intent = Bundle()
        intent.putString("name","666")
        ULogger.i(intent)

    }
    override fun onLastRight(view: View) {
        start(TestPictureFragment())
    }
    /**当前业务部分 end*/

}