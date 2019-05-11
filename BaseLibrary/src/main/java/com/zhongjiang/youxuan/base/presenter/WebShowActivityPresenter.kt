package com.zhongjiang.youxuan.base.presenter

import com.zhongjiang.youxuan.base.presenter.webshowactivity.WebShowActivityContract
import javax.inject.Inject

class WebShowActivityPresenter @Inject constructor(view:WebShowActivityContract.View,model:WebShowActivityContract.Model):BasePresenter<WebShowActivityContract.View,WebShowActivityContract.Model>(view,model),WebShowActivityContract.Presenter