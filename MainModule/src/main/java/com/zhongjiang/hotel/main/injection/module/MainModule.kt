package com.zhongjiang.hotel.main.injection.module

import com.zhongjiang.hotel.base.injection.component.BaseActivityComponent
import com.zhongjiang.hotel.base.injection.component.BaseFragmentComponent
import com.zhongjiang.hotel.base.injection.scope.ActivityScope
import com.zhongjiang.hotel.main.ui.activity.MainSingleFragmentActivity
import com.zhongjiang.hotel.main.ui.fragment.tabmain.TabMainFragment
import com.zhongjiang.hotel.main.ui.fragment.testpicture.TestPictureFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @date on 2019/05/08 09:04
 * @packagename
 * @author dyn
 * @org com.zhongjiang.youxuan
 * @describe 模块对外暴露的Module
 * @email 583454199@qq.com
 **/
@Module(subcomponents = [BaseFragmentComponent::class, BaseActivityComponent::class], includes = [MainModuleServiceModule::class])
abstract class MainModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [MainViewModule::class])
    abstract fun contributeSplashFragmentInjector(): TabMainFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainViewModule::class])
    abstract fun contributeTestPictureFragmentInjector(): TestPictureFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainViewModule::class])
    abstract fun contributeSplashActivityInjector(): MainSingleFragmentActivity

}