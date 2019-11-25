package com.zhongjiang.hotel.provider.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by SSD董 on 2017/3/30.
 */
@Entity
class SplashAdEntity {
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

    fun getIsShare(): Int {
        return isShare
    }

    /**
     * @param splashAdEntity 把这个新的bean 克隆给自己
     * */
    fun clone(splashAdEntity: SplashAdEntity): SplashAdEntity {
        this.id = splashAdEntity.id
        this.imgTitle = splashAdEntity.imgTitle
        this.imgPathUrl = splashAdEntity.imgPathUrl
        this.imgPageUrl = splashAdEntity.imgPageUrl
        this.pageStatus = splashAdEntity.pageStatus
        this.appType = splashAdEntity.appType
        this.showTime = splashAdEntity.showTime
        this.funcType = splashAdEntity.funcType
        this.isShare = splashAdEntity.isShare
        this.shareTitle = splashAdEntity.shareTitle
        this.shareContents = splashAdEntity.shareContents
        this.sharePic = splashAdEntity.sharePic
        this.shareUrl = splashAdEntity.shareUrl
        return this
    }

}
