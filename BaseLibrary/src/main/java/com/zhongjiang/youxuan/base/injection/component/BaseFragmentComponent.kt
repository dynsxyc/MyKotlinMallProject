package com.zhongjiang.youxuan.base.injection.component

import com.zhongjiang.youxuan.base.ui.fragment.BaseInjectFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by dyn on 2018/7/17.
 */
@Subcomponent(modules = arrayOf(AndroidSupportInjectionModule::class))
interface BaseFragmentComponent :AndroidInjector<BaseInjectFragment>{
    @Subcomponent.Builder
abstract class Builder : AndroidInjector.Builder<BaseInjectFragment>()
}