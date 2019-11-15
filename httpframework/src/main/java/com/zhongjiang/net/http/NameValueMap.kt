package com.zhongjiang.net.http

interface NameValueMap<K, T> : MutableMap<K, T> {
    fun set(key: K, value: T)
    fun setAll(mutableMap: MutableMap<K, T>)
}