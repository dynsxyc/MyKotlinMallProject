package com.zhongjiang.hotel.base.utils.gsontypeadapter

import com.google.gson.*
import java.lang.reflect.Type
import com.google.gson.JsonSyntaxException



/**
 * @author dyn
 * @date on 2019/8/12  11:36
 * @packagename com.zhongjiang.youxuan.base.utils.gsontypeadapter
 * @fileName LongDefaultAdapter
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
class LongDefaultAdapter : JsonSerializer<Long>, JsonDeserializer<Long> {
    override fun serialize(src: Long?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src)
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext?): Long {
        try {
            if (json.asString == "" || json.asString == "null") {//定义为long类型,如果后台返回""或者null,则返回0
                return 0
            }
        } catch (ignore: Exception) {
        }

        try {
            return json.asLong
        } catch (e: NumberFormatException) {
            throw JsonSyntaxException(e)
        }

    }
}