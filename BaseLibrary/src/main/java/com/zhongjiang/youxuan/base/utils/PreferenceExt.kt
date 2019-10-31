package com.zhongjiang.youxuan.base.utils

import android.app.Application
import android.content.Context
import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author dyn
 * @date on 2019/10/24  18:00
 * @packagename com.zhongjiang.youxuan.base.utils
 * @fileName PreferenceExt
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
class Preference<T>(val context:Context, val name:String, private val default:T, private val prefName:String = "default"):ReadWriteProperty<Any?,T>{
    private val prefs by lazy {
        context.getSharedPreferences(prefName,Context.MODE_PRIVATE)
    }
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name)
    }
    private fun findPreference(key:String):T{
       return when(default){
            is Long -> prefs.getLong(key,default)
            is String -> prefs.getString(key,default)
            is Boolean -> prefs.getBoolean(key,default)
            is Int -> prefs.getInt(key,default)
            is Float -> prefs.getFloat(key,default)
           else -> throw IllegalArgumentException("不支持的类型")
       } as T
    }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name,value)
    }

    private fun putPreference(key:String,value:T){
        with(prefs.edit()){
            when(value){
                is Long -> putLong(key,value)
                is String -> putString(key,value)
                is Boolean -> putBoolean(key,value)
                is Int -> putInt(key,value)
                is Float -> putFloat(key,value)
                else -> throw IllegalArgumentException("不支持的类型")
            }
        }

    }
}