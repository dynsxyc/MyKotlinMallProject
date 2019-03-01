package com.zhongjiang.kotlin.mall.injection.module

import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.mall.presenter.mainfragment.MainFragmentContract
import com.zhongjiang.kotlin.mall.presenter.mainfragment.MainFragmentModel
import com.zhongjiang.kotlin.mall.presenter.mainfragment.MainFragmentPresenter
import com.zhongjiang.kotlin.mall.ui.fragment.MainFragment
import dagger.Module
import dagger.Provides

@Module
class MainFragmentModule {
    @Provides
    @ActivityScope
    fun provideMainFragmentView(splashFragment: MainFragment): MainFragmentContract.View {
        return splashFragment
    }

    @Provides
    @ActivityScope
    fun provideMainFragmentModel(mainFragmentModel: MainFragmentModel): MainFragmentContract.Model {
        return mainFragmentModel
    }
    @Provides
    @ActivityScope
    fun provideMainFragmentPresenter(mainFragmentPresenter: MainFragmentPresenter): MainFragmentContract.Presenter {
        return mainFragmentPresenter
    }
}