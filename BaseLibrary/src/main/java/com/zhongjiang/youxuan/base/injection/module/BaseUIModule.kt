package com.zhongjiang.youxuan.base.injection.module

import com.zhongjiang.youxuan.base.injection.scope.ActivityScope
import com.zhongjiang.youxuan.base.ui.activity.web.WebShowActivityContract
import com.zhongjiang.youxuan.base.ui.activity.web.WebShowActivityModel
import com.zhongjiang.youxuan.base.ui.activity.web.WebShowActivity
import com.zhongjiang.youxuan.base.ui.fragment.YXWebShowFragment
import dagger.Module
import dagger.Provides

@Module
class BaseUIModule {
    @Provides
    @ActivityScope
    fun provideWebShowActivityView(webshowView: WebShowActivity): WebShowActivity {
        return webshowView
    }

    @Provides
    @ActivityScope
    fun provideWebShowActivityModel(webshowModel: WebShowActivityModel): WebShowActivityModel {
        return webshowModel
    }
    @Provides
    @ActivityScope
    fun provideWebShowFragmentModel(webshowFragment: YXWebShowFragment): YXWebShowFragment {
        return webshowFragment
    }

}