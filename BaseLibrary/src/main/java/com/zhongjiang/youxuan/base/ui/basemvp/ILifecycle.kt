package com.zhongjiang.youxuan.base.ui.basemvp

import android.content.res.Configuration
import android.os.Bundle
/**
 * 生命周期函数
 * */
interface ILifecycle {

    fun onCreate(saveInstanceState: Bundle?)

    fun onSaveInstanceState(outState: Bundle?)

    fun onViewStateRestored(saveInstanceState: Bundle?)

    fun onConfigurationChange(newConfig: Configuration)

    fun onDestroy()

    fun onStart()

    fun onStop()

    fun onResume()

    fun onPause()
}