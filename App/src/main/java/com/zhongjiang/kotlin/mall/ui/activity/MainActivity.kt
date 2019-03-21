package com.zhongjiang.kotlin.mall.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.orhanobut.logger.Logger
import com.zhongjiang.kotlin.base.ui.activity.BaseInjectActivity
import com.zhongjiang.kotlin.mall.R
import com.zhongjiang.kotlin.mall.ui.fragment.MainFragment
import com.zhongjiang.kotlin.provider.router.RouterPath

@Route(path = RouterPath.MainCenter.PATH_MAIN)
class MainActivity : BaseInjectActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i(TAG,"onCreate");
        setContentView(R.layout.activity_main)
        initView()
    }
    fun initView() {
        loadRootFragment(R.id.mainActivity_rl_content, MainFragment.newInstance())
    }

    override fun getSwipeBackEnable(): Boolean {
        return false
    }


}
