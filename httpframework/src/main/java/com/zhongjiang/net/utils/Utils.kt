package com.zhongjiang.net.utils

object Utils {
    fun isExist(className:String,loader: ClassLoader?):Boolean{
        try {

            Class.forName(className)
            return true
        }catch (e:ClassNotFoundException){
            return false
        }
    }
}