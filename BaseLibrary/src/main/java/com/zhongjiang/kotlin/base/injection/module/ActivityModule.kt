package com.zhongjiang.kotlin.base.injection.module

import android.app.Activity
import com.zhongjiang.kotlin.base.injection.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Created by dyn on 2018/7/17.
 */
@Module
class ActivityModule(private val activity: Activity) {
    @Provides
    @ActivityScope
    fun providesActivity(): Activity {
        return activity
    }
}