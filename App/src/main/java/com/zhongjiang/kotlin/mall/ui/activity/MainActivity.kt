package com.zhongjiang.kotlin.mall.ui.activity

import com.jakewharton.rxbinding2.view.RxView
import com.zhongjiang.kotlin.base.ext.shieldDoubleClick
import com.zhongjiang.kotlin.base.ui.activity.BaseMvpActivity
import com.zhongjiang.kotlin.mall.R
import com.zhongjiang.kotlin.mall.presenter.MainActivityPresenter
import com.zhongjiang.kotlin.mall.presenter.contract.MainActivityContract
import com.zhongjiang.kotlin.provider.event.LoginSuccessEvent
import com.zhongjiang.kotlin.provider.router.NavigationUtil
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class MainActivity : BaseMvpActivity<MainActivityPresenter>(), MainActivityContract.View {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }

    @Inject
    lateinit var navigationUtil: NavigationUtil

    override fun initView() {
        RxView.clicks(testTv).shieldDoubleClick {
            navigationUtil.navigationToLogin(true)
        }
        mRxBus.toObservable(LoginSuccessEvent::class.java, Consumer {
            toast("登录成功")
        }, bindLifecycle())
    }


}
