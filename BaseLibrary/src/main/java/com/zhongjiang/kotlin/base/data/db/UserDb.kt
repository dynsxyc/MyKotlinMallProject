package com.zhongjiang.kotlin.base.data.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class UserDb constructor() {
    constructor(id: Long, name: String) : this() {
        this.name = name
        this.id = id
    }

    @Id(assignable = true)
    var id: Long = 0
    var name: String = ""
}