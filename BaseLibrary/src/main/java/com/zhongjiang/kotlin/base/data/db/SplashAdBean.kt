package com.zhongjiang.kotlin.base.data.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by SSD董 on 2017/3/30.
 */
@Entity
class SplashAdBean constructor(){
    @Id(assignable = true)
    var id: Long = 0

    var imgTitle: String = ""

    var imgPathUrl: String = ""

    var imgPageUrl: String = ""

    var pageStatus: Int = 0

    var appType: Int = 0

    var showTime: Int = 0

    var funcType: Int = 0

    var isShare: Int = 0

    var shareTitle: String = ""

    var shareContents: String = ""

    var sharePic: String = ""

    var shareUrl: String = ""

    fun getIsShare():Int{
        return isShare
    }

    /**
     * @param splashAdBean 把这个新的bean 克隆给自己
     * */
    fun clone(splashAdBean: SplashAdBean): SplashAdBean {
        this.id = splashAdBean.id
        this.imgTitle = splashAdBean.imgTitle
        this.imgPathUrl = splashAdBean.imgPathUrl
        this.imgPageUrl = splashAdBean.imgPageUrl
        this.pageStatus = splashAdBean.pageStatus
        this.appType = splashAdBean.appType
        this.showTime = splashAdBean.showTime
        this.funcType = splashAdBean.funcType
        this.isShare = splashAdBean.isShare
        this.shareTitle = splashAdBean.shareTitle
        this.shareContents = splashAdBean.shareContents
        this.sharePic = splashAdBean.sharePic
        this.shareUrl = splashAdBean.shareUrl
        return this
    }

}
