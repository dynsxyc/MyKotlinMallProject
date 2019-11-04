package com.zhongjiang.youxuan.base.presenter

import android.content.res.Configuration
import android.os.Bundle

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