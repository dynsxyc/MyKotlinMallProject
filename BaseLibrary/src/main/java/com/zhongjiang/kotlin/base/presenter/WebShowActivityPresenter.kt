package com.zhongjiang.kotlin.base.presenter

import com.zhongjiang.kotlin.base.presenter.webshowactivity.WebShowActivityContract
import javax.inject.Inject

class WebShowActivityPresenter @Inject constructor(view:WebShowActivityContract.View,model:WebShowActivityContract.Model):BasePresenter<WebShowActivityContract.View,WebShowActivityContract.Model>(view,model),WebShowActivityContract.Presenter