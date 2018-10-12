package com.zhongjiang.kotlin.user.data.protocol

/**
 * Created by dyn on 2018/7/16.
 */
data class UserInfo(val id:String,
                    val userIcon:String,
                    val userName:String,
                    val userGender:String,
                    val userMobile:String,
                    val userSign:String){
    var nameStatus:Int = (if(id == "0")  2 else 3)


}