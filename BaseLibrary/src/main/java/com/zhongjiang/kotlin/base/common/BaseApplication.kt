package com.zhongjiang.kotlin.base.common

import android.app.Activity
import android.app.Fragment
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ContentProvider
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.zhongjiang.kotlin.base.BuildConfig
import com.zhongjiang.kotlin.base.common.YouXuanNetInterfaceConstant.Companion.BASE_URL_DEVELOP_TEST
import com.zhongjiang.kotlin.base.data.net.interceptor.HttpRequestHandler
import com.zhongjiang.kotlin.base.injection.WindowScreenInfo
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
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>
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

    override fun supportFragmentInjector(): DispatchingAndroidInjector<androidx.fragment.app.Fragment>? {
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
                .baseurl(BASE_URL_DEVELOP_TEST)
                .addInterceptor(LoggingInterceptor.Builder()
                        .loggable(BuildConfig.DEBUG)
                        .setLevel(Level.BASIC)
                        .log(Platform.INFO)
                        .request("Request")
                        .response("Response")
                        .build())
                .setWindowScreenInfo(getScreenInfo())
                .globeHttpHandler(HttpRequestHandler.EMPTY)
                .build()
    }

    protected fun getHttpClientModule(): HttpClientModule {
        return HttpClientModule()
    }

    protected fun getCacheModule(): CacheModule {
        return CacheModule(ContextCompat.getExternalCacheDirs(this)[0])
    }

    /**
     * Return the width of screen, in pixel.
     *
     * @return the width of screen, in pixel
     */
    fun getScreenInfo(): WindowScreenInfo {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            wm.defaultDisplay.getRealSize(point)
        } else {
            wm.defaultDisplay.getSize(point)
        }
        return WindowScreenInfo(point.x, point.y)
    }
}