package com.zhongjiang.net.service.convert

import com.google.gson.Gson
import com.zhongjiang.net.http.HttpResponse
import java.io.InputStreamReader
import java.lang.reflect.Type

class JsonConvert :Convert {
    override fun pares(value: String, type: Type): Any {
        return mGson.fromJson(value,type)

    }

    override fun isConvert(contentType: String?): Boolean {
        return CONTENT_TYPE.equals(contentType,true)
    }

    companion object{
        const val CONTENT_TYPE = "application/json;charset=UTF-8"
        val mGson = Gson()
    }
    override fun pares(httpResponse: HttpResponse, type: Type): Any {
        var reader = InputStreamReader(httpResponse.getBody())
        return mGson.fromJson(reader,type)
    }
}