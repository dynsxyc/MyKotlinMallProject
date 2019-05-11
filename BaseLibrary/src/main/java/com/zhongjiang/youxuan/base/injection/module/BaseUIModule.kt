package com.zhongjiang.youxuan.base.injection.module

import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.base.presenter.webshowactivity.WebShowActivityContract
import com.zhongjiang.youxuan.base.presenter.webshowactivity.WebShowActivityModel
import com.zhongjiang.youxuan.base.ui.activity.WebShowActivity
import com.zhongjiang.youxuan.base.ui.fragment.YXWebShowFragment
import dagger.Module
import dagger.Provides

@Module
class BaseUIModule {
    @Provides
    @ActivityScope
    fun provideWebShowActivityView(webshowView: WebShowActivity): WebShowActivityContract.View {
        return webshowView
    }

    @Provides
    @ActivityScope
    fun provideWebShowActivityModel(webshowModel: WebShowActivityModel): WebShowActivityContract.Model {
        return webshowModel
    }
    @Provides
    @ActivityScope
    fun provideWebShowFragmentModel(webshowFragment: YXWebShowFragment): YXWebShowFragment {
        return webshowFragment
    }

}