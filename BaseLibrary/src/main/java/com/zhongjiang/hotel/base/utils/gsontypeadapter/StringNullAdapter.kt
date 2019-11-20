package com.zhongjiang.hotel.base.utils.gsontypeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 * @author dyn
 * @date on 2019/8/12  10:40
 * @packagename com.zhongjiang.youxuan.base.utils.gsontypeadapter
 * @fileName StringNullAdapter
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
class StringNullAdapter : TypeAdapter<String>() {
    override fun write(out: JsonWriter?, value: String?) {
        if (value == null) {
            out?.nullValue()
            return
        }
        out?.value(value)
    }

    override fun read(reader : JsonReader?): String? {
        if (reader?.peek() === JsonToken.NULL) {
            reader.nextNull()
            return ""//原先是返回Null，这里改为返回空字符串
        }

        val jsonStr = reader?.nextString()
        return if (jsonStr == "null") {
            ""
        } else {
           jsonStr
        }
    }
}