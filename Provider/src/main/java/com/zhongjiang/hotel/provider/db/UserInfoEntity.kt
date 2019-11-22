package com.zhongjiang.hotel.provider.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
/**数据库 类名、属性重命名要看文档 不能随意修改字段名称  否则启动报错*/
@Entity
class UserInfoEntity {
    var nickName: String = ""
    var ticket: String = ""
    var userName: String = ""
    var headImg: String = ""
    var isCreate: Int = 0
    @Id(assignable = true)
    var userId: Long = 0

    fun getIsCreate(): Int {
        return isCreate
    }

    fun clone(userInfoEntity: UserInfoEntity) {
        this.userId = userInfoEntity.userId
        this.ticket = userInfoEntity.ticket
        this.userName = userInfoEntity.userName
        this.nickName = userInfoEntity.nickName
        this.headImg = userInfoEntity.headImg
        this.isCreate = userInfoEntity.isCreate
    }
}