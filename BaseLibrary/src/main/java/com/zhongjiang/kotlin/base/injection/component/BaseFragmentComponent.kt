package com.zhongjiang.kotlin.base.injection.component

import com.zhongjiang.kotlin.base.ui.fragment.BaseFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by dyn on 2018/7/17.
 */
@Subcomponent(modules = arrayOf(AndroidSupportInjectionModule::class))
interface BaseFragmentComponent :AndroidInjector<BaseFragment>{
    @Subcomponent.Builder
abstract class Builder : AndroidInjector.Builder<BaseFragment>()
}