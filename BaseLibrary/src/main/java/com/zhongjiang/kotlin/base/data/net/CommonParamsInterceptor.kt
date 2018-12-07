package com.zhongjiang.kotlin.base.data.net

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zhongjiang.kotlin.base.NetWorkUtils
import com.zhongjiang.kotlin.base.data.NetConstantField.Companion.API_DATA_NONCE
import com.zhongjiang.kotlin.base.data.NetConstantField.Companion.API_DATA_SIGNATURE
import com.zhongjiang.kotlin.base.data.NetConstantField.Companion.API_DATA_TICKET
import com.zhongjiang.kotlin.base.data.NetConstantField.Companion.API_DATA_TIMESTAMP
import com.zhongjiang.kotlin.base.data.NetConstantField.Companion.API_DATA_V
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException

/**
 * Created by dyn on 2018/10/10.
 */

open class CommonParamsInterceptor constructor(val mGson: Gson) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val method = request.method()

        try {

            //公共参数
            val commomParamsMap = HashMap<String, String>()

            commomParamsMap.put(API_DATA_TIMESTAMP, "1539251402138")
            commomParamsMap.put(API_DATA_NONCE, "967985")
            commomParamsMap.put(API_DATA_V, "f05e4d4e017a40ed9c810a64664f0b90|Android|5.0_21|20|3.0.2")
            commomParamsMap.put(API_DATA_TICKET, "d69ac27116d294647804bbf4ba1b90ae1fe8d412b7cc3e87fed09915a91f3360830d63f3a427e17f8696074aadbfef61")

            var rootMap = HashMap<String, String>()
            //公共参数加进去
            rootMap.putAll(commomParamsMap)
            if ("GET" == method) {
                val mHttpUrl = request.url()
                val paramNames = mHttpUrl.queryParameterNames()
                paramNames.forEach {
                    mHttpUrl.queryParameter(it)?.let { it1 -> rootMap.put(it, it1) }
                }
                //签名
                rootMap.put(API_DATA_SIGNATURE, NetWorkUtils.getSign(rootMap, "4c1dde4fa3bcd0c1c03a637c95adb593"))

                var url = mHttpUrl.toString()
                val index = url.indexOf("?")
                if (index > 0) {
                    url = url.substring(0, index)
                }
                val builderStr = StringBuilder()
                val iterator = rootMap.iterator()

                while (iterator.hasNext()) {
                    val next = iterator.next()
                    if (!iterator.hasNext()) {
                        builderStr.append(next.key + "=" + next.value)
                    }else{
                        builderStr.append(next.key + "=" + next.value + "&")

                    }
                }
                url = url + "?" + builderStr.toString()

                //重新构建请求
                request = request.newBuilder().url(url).build()
            } else if ("POST" == method) {
                val body = request.body()
                if (body is FormBody) {
                    for (i in 0 until body.size()) {
                        rootMap.put(body.encodedName(i), body.encodedValue(i))
                    }
                } else {
                    val buffer = Buffer()
                    body!!.writeTo(buffer)
                    val oldJsonParams = buffer.readUtf8()
                    val map = mGson.fromJson<HashMap<String, String>>(
                            oldJsonParams,
                            object : TypeToken<HashMap<String, String>>() {}.type
                    )
                    rootMap.putAll(map)
                    rootMap.put(API_DATA_SIGNATURE, NetWorkUtils.getSign(rootMap, "4c1dde4fa3bcd0c1c03a637c95adb593"))

                    val formBody = FormBody.Builder()
                    rootMap.forEach({
                        formBody.add(it.key, it.value)
                    })
                    request = request.newBuilder().post(formBody.build()).url(request.url()).build()
                }


            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return chain.proceed(request)
    }

}
