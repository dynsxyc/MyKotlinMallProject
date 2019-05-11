package com.zhongjiang.youxuan.user.injection.module

import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.user.presenter.mainfragment.MainFragmentContract
import com.zhongjiang.youxuan.user.presenter.mainfragment.MainFragmentModel
import com.zhongjiang.youxuan.user.presenter.mainfragment.MainFragmentPresenter
import com.zhongjiang.youxuan.user.ui.fragment.MainFragment
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