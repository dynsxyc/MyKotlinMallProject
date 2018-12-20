package com.zhongjiang.kotlin.base.data.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity class AdDb{
    @Id
    var id:Long = 0
    var name:String = ""
}