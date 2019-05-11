package com.zhongjiang.youxuan.base.presenter.webshowactivity

import com.zhongjiang.youxuan.base.data.net.service.GlobalServiceManager
import com.zhongjiang.youxuan.base.presenter.BaseModel
import javax.inject.Inject

class WebShowActivityModel @Inject constructor(manager: GlobalServiceManager) : BaseModel<GlobalServiceManager>(manager), WebShowActivityContract.Model {

    }