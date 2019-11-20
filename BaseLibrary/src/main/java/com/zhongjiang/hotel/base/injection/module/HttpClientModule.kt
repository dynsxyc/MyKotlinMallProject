package com.zhongjiang.hotel.base.injection.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zhongjiang.hotel.base.data.net.interceptor.RequestInterceptor
import com.zhongjiang.hotel.base.utils.gsontypeadapter.DoubleDefaultAdapter
import com.zhongjiang.hotel.base.utils.gsontypeadapter.IntDefaultAdapter
import com.zhongjiang.hotel.base.utils.gsontypeadapter.LongDefaultAdapter
import com.zhongjiang.hotel.base.utils.gsontypeadapter.StringNullAdapter
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by QingMei on 2017/8/15.
 * desc:
 */
@Module
class HttpClientModule {


    val TIME_OUT_SECONDS = 20

    @Singleton
    @Provides
    fun provideRetrofit(builder: Retrofit.Builder, client: OkHttpClient, httpUrl: HttpUrl,gson: Gson): Retrofit {
        return builder
                .baseUrl(httpUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Singleton
    @Provides
    fun provideClient(okHttpClient: OkHttpClient.Builder, intercept: Interceptor, interceptors: List<@JvmSuppressWildcards Interceptor>): OkHttpClient {
        val builder = okHttpClient
                .connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .addNetworkInterceptor(intercept)
        if (interceptors.isNotEmpty()) {
            for (interceptor in interceptors) {
                builder.addInterceptor(interceptor)
            }
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    @Singleton
    @Provides
    fun provideClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Singleton
    @Provides
    fun provideIntercept(interceptor: RequestInterceptor): Interceptor {
        return interceptor
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
                .registerTypeAdapter(Integer::class.java, IntDefaultAdapter())
                .registerTypeAdapter(Double::class.java, DoubleDefaultAdapter())
                .registerTypeAdapter(Long::class.java, LongDefaultAdapter())
                .registerTypeAdapter(String::class.java, StringNullAdapter())
                .create()
    }

}
