package com.zhongjiang.kotlin.base.common

import android.app.Activity
import android.app.Fragment
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ContentProvider
import android.support.multidex.MultiDexApplication
import android.support.v4.content.ContextCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.zhongjiang.kotlin.base.BuildConfig
import com.zhongjiang.kotlin.base.data.net.interceptor.HttpRequestHandler
import com.zhongjiang.kotlin.base.injection.module.AppModule
import com.zhongjiang.kotlin.base.injection.module.CacheModule
import com.zhongjiang.kotlin.base.injection.module.GlobalConfigModule
import com.zhongjiang.kotlin.base.injection.module.HttpClientModule
import dagger.android.*
import dagger.android.support.HasSupportFragmentInjector
import okhttp3.internal.platform.Platform
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/17.
 */
abstract class BaseApplication : MultiDexApplication(), HasActivityInjector,
        HasBroadcastReceiverInjector,
        HasFragmentInjector,
        HasServiceInjector,
        HasContentProviderInjector,
        HasSupportFragmentInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<android.support.v4.app.Fragment>
    @Inject
    lateinit var broadcastReceiverInjector: DispatchingAndroidInjector<BroadcastReceiver>
    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>
    @Inject
    lateinit var contentProviderInjector: DispatchingAndroidInjector<ContentProvider>


    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityInjector
    }

    override fun broadcastReceiverInjector(): DispatchingAndroidInjector<BroadcastReceiver>? {
        return broadcastReceiverInjector
    }

    override fun fragmentInjector(): DispatchingAndroidInjector<Fragment>? {
        return fragmentInjector
    }

    override fun serviceInjector(): DispatchingAndroidInjector<Service>? {
        return serviceInjector
    }

    override fun contentProviderInjector(): DispatchingAndroidInjector<ContentProvider>? {
        return contentProviderInjector
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<android.support.v4.app.Fragment>? {
        return supportFragmentInjector
    }

    override fun onCreate() {
        super.onCreate()
        initRouter()
        initDi()
    }

    private fun initDi() {
        instance = this
        initAppInjection()
    }

    /**
     * 初始化ARouter
     * <p>
     * {@link BuildConfig} 决定是否开启调试模式
     * (如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
     */
    private fun initRouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
    /**
     * 这是类库底层的injectApp代码示例，你应该在你的Module中重写该方法:
     * <p>
     * DaggerBaseAppComponent.builder()
     * .cacheModule(getCacheModule())
     * .appModule(getAppModule())
     * .globalConfigModule(getGlobalConfigModule())
     * .httpClientModule(getHttpClientModule())
     * .serviceModule(getServiceModule())
     * .build()
     * .initDI(this);
     */
    abstract fun initAppInjection()

    companion object {
        lateinit var instance: BaseApplication
    }


    protected fun getAppModule(): AppModule {
        return AppModule(this)
    }

    protected fun getGlobalConfigModule(): GlobalConfigModule {
        return GlobalConfigModule.Buidler()
                .baseurl("https://api.github.com/")
                .addInterceptor(LoggingInterceptor.Builder()
                        .loggable(BuildConfig.DEBUG)
                        .setLevel(Level.BASIC)
                        .log(Platform.INFO)
                        .request("Request")
                        .response("Response")
                        .build())
                .globeHttpHandler(HttpRequestHandler.EMPTY)
                .build()
    }

    protected fun getHttpClientModule(): HttpClientModule {
        return HttpClientModule()
    }

    protected fun getCacheModule(): CacheModule {
        return CacheModule(ContextCompat.getExternalCacheDirs(this)[0])
    }
}