package com.zhongjiang.kotlin.base.injection.module

import android.text.TextUtils
import com.zhongjiang.kotlin.base.data.net.interceptor.HttpRequestHandler
import com.zhongjiang.kotlin.base.injection.WindowScreenInfo
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import java.io.File
import java.util.*
import javax.inject.Singleton


/**
 * Created by Glooory on 17/5/15.
 */
@Module
class GlobalConfigModule  constructor(var buidler: Buidler) {

    @Singleton
    @Provides
    fun provideInterceptors(): List<@JvmSuppressWildcards Interceptor> {
        return buidler.interceptors
    }

    @Singleton
    @Provides
    fun provideBaseUrl(): HttpUrl {
        return buidler.apiUrl
    }

    @Singleton
    @Provides
    fun provideWindowScreenInfo(): WindowScreenInfo {
        return buidler.screenInfo
    }

    @Singleton
    @Provides
    fun provideHttpRequestHandler(): HttpRequestHandler? {
        return buidler.handler // HttpRequestHandler.EMPTY//打印请求信息
    }

    class Buidler () {
        lateinit var apiUrl:HttpUrl
        var interceptors = ArrayList<@JvmSuppressWildcards Interceptor>()
        lateinit var handler: HttpRequestHandler
        lateinit var cacheFile: File
        lateinit var screenInfo: WindowScreenInfo

        fun baseurl(baseurl: String): Buidler {
            if (TextUtils.isEmpty(baseurl)) {
                throw IllegalArgumentException("baseurl can not be empty")
            }
            this.apiUrl = HttpUrl.parse(baseurl)!!
            return this
        }

        fun globeHttpHandler(handler: HttpRequestHandler): Buidler {// handle the http response before displaying it to users
            this.handler = handler
            return this
        }

        fun addInterceptor(interceptor: Interceptor): Buidler {
            this.interceptors.add(interceptor)
            return this
        }

        fun setWindowScreenInfo(screenInfo: WindowScreenInfo):Buidler{
            this.screenInfo =  screenInfo
            return this
        }

        fun cacheFile(cacheFile: File): Buidler {
            this.cacheFile = cacheFile
            return this
        }

        fun build(): GlobalConfigModule {
            return GlobalConfigModule(this)
        }
    }


}
