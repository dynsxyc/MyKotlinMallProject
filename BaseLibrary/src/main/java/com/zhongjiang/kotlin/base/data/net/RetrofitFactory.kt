package com.zhongjiang.kotlin.base.data.net

import android.os.Build
import android.webkit.WebSettings
import com.google.gson.GsonBuilder
import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.common.BaseConstant.Companion.SERVER_ADDRESS
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by dyn on 2018/7/16.
 */
class RetrofitFactory private constructor() {
    companion object {
        val instance: RetrofitFactory by lazy {
            RetrofitFactory()
        }
    }

    private var retrofit: Retrofit
    private var headerInterceptor: Interceptor;
    //初始化
    init {
        //通用拦截
        headerInterceptor = Interceptor { chain ->
            val oldRequest = chain.request()
            val request = oldRequest.newBuilder().addHeader("Content-Type", "application/json").addHeader("charset", "utf-8").removeHeader("User-Agent").addHeader("User-Agent",getUserAgent()).build()
            chain.proceed(request)
        }
        retrofit = Retrofit.Builder()
                .baseUrl(SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient()).build()
    }

    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(CommonParamsInterceptor(GsonBuilder().enableComplexMapKeySerialization().create()))
                .addInterceptor(initLogInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
    }

    private fun initLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor;
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    fun getUserAgent(): String {
        var userAgent = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(BaseApplication.context)
            } catch (e: Exception) {
                userAgent = System.getProperty("http.agent")
            }

        } else {
            userAgent = System.getProperty("http.agent")
        }
        val sb = StringBuffer()
        var i = 0
        val length = userAgent.length
        while (i < length) {
            val c = userAgent[i]
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", c.toInt()))
            } else {
                sb.append(c)
            }
            i++
        }
        return sb.toString()
    }
}