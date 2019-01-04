package com.zhongjiang.kotlin.base.injection.module

import com.zhongjiang.kotlin.base.injection.scope.ActivityScope
import com.zhongjiang.kotlin.base.presenter.webshowactivity.WebShowActivityContract
import com.zhongjiang.kotlin.base.presenter.webshowactivity.WebShowActivityModel
import com.zhongjiang.kotlin.base.ui.activity.WebShowActivity
import dagger.Module
import dagger.Provides

@Module
class BaseUIModule {
        @Provides
        @ActivityScope
        fun provideWebShowActivityView(webshowView: WebShowActivity): WebShowActivityContract.View {
            return webshowView
        }  @Provides
        @ActivityScope
        fun provideWebShowActivityModel(webshowModel: WebShowActivityModel): WebShowActivityContract.Model {
            return webshowModel
        }

}