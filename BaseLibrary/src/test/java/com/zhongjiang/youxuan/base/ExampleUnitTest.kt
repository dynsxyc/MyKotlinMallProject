package com.zhongjiang.youxuan.base

import com.zhongjiang.youxuan.base.ext.no
import com.zhongjiang.youxuan.base.ext.otherwise
import com.zhongjiang.youxuan.base.ext.yes
import org.junit.Assert
import org.junit.Test

/**
 * @author dyn
 * @date on 2019/10/24  16:45
 * @packagename com.zhongjiang.youxuan.base
 * @fileName ExampleUnitTest
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
class ExampleUnitTest {
    @Test
    fun testBoolean(){
        val  other = false.yes {
            1
        }.otherwise {
            2
        }
        Assert.assertEquals(other,2)
//        val  yes = true.yes {
//            "33"
//        }.otherwise {
//            "555"
//        }
//        Assert.assertEquals(yes,"33")
//        val  no1 = false.no {
//            "33"
//        }.otherwise {
//            "555"
//        }
//        Assert.assertEquals(no1,"33")
//        val  haha = true.no {
//            "33"
//        }.otherwise {
//            "555"
//        }
//        Assert.assertEquals(haha,"555")
    }
}