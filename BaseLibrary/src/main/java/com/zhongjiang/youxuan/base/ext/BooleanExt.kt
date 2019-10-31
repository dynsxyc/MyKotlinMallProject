package com.zhongjiang.youxuan.base.ext

/**
 * @author dyn
 * @date on 2019/10/24  16:40
 * @packagename com.zhongjiang.youxuan.base.ext
 * @fileName BooleanExt
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
sealed class BooleanExt<out T>

object Otherwise : BooleanExt<Nothing>()
class WithData<T>(val value: T) : BooleanExt<T>()

fun <T> Boolean.yes(block: () -> T) =
        when {
            this -> WithData(block())
            else -> Otherwise
        }


inline fun <T>Boolean.no(block: () -> T):BooleanExt<T> =
    when{
        this ->Otherwise
        else ->WithData(block())
    }


inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
        when (this) {
            is WithData -> this.value
            is Otherwise -> block()

        }

fun main() {
    val  yes = true.yes {
        "33"
    }.otherwise {
        "555"
    }
}
