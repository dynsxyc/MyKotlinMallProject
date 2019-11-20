package com.zhongjiang.hotel.base.injection.component

import com.zhongjiang.youxuan.base.ui.activity.BaseInjectActivity
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

/**
 * Created by dyn on 2018/7/17.
 */
@Subcomponent(modules = [AndroidInjectionModule::class])
interface BaseActivityComponent : AndroidInjector<BaseInjectActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<BaseInjectActivity>()
}