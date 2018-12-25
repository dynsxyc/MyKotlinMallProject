package com.zhongjiang.kotlin.base.data.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class UserInfoEntity {
    var nickname: String = ""
    var ticket: String = ""
    var username: String = ""
    var headImg: String = ""
    var isCreate: Int = 0
    @Id
    var userId: Long = 0

    fun getIsCreate(): Int {
        return isCreate
    }

    fun clone(userInfoEntity: UserInfoEntity) {
        this.userId = userInfoEntity.userId
        this.ticket = userInfoEntity.ticket
        this.username = userInfoEntity.username
        this.nickname = userInfoEntity.nickname
        this.headImg = userInfoEntity.headImg
        this.isCreate = userInfoEntity.isCreate
    }
}