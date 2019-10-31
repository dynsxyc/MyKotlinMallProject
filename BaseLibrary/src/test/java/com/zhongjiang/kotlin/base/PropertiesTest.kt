package com.zhongjiang.kotlin.base

import com.zhongjiang.youxuan.base.common.AbsProperties
import org.junit.Test

/**
 * @author dyn
 * @date on 2019/10/25  15:45
 * @packagename com.zhongjiang.kotlin.base
 * @fileName PropertiesTest
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
class InfoProps: AbsProperties("info.properties"){
    var name:String by prop
    var email:String by prop
    var age:Int by prop
    var student:Boolean by prop
    var point:Float by prop
}
class TestProperties{
    @Test
    fun testProperties(){
        InfoProps().let {
            println(it.name)
            println(it.email)
            println(it.age)
            println(it.student)
            println(it.point)
            it.name = "名字修改"
            it.email = "666"
            it.age = 3
            it.student = true
            it.point = 5.5f
            println(it.name)

        }
    }
}