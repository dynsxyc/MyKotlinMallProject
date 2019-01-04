package com.zhongjiang.kotlin.base.presenter.webshowactivity

import com.zhongjiang.kotlin.base.data.net.service.GlobalServiceManager
import com.zhongjiang.kotlin.base.presenter.BaseModel
import javax.inject.Inject

class WebShowActivityModel @Inject constructor(manager: GlobalServiceManager) : BaseModel<GlobalServiceManager>(manager), WebShowActivityContract.Model {

    }