package com.zhongjiang.hotel.base.common

import android.app.Activity
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ContentProvider
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.LogUtils
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.zhongjiang.hotel.base.common.YouXuanNetInterfaceConstant.Companion.BASE_URL_DEVELOP_TEST
import com.zhongjiang.hotel.base.data.net.interceptor.HttpRequestHandler
import com.zhongjiang.hotel.base.injection.WindowScreenInfo
import com.zhongjiang.hotel.base.injection.module.AppModule
import com.zhongjiang.hotel.base.injection.module.CacheModule
import com.zhongjiang.hotel.base.injection.module.GlobalConfigModule
import com.zhongjiang.hotel.base.injection.module.HttpClientModule
import com.zhongjiang.youxuan.base.BuildConfig
import dagger.android.*
import dagger.android.support.HasSupportFragmentInjector
import okhttp3.internal.platform.Platform
import javax.inject.Inject


/**
 * Created by dyn on 2018/7/17.
 */
abstract class BaseApplication : MultiDexApplication(), HasActivityInjector,
        HasBroadcastReceiverInjector,
        HasServiceInjector,
        HasContentProviderInjector,
        HasSupportFragmentInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
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

    override fun serviceInjector(): DispatchingAndroidInjector<Service>? {
        return serviceInjector
    }

    override fun contentProviderInjector(): DispatchingAndroidInjector<ContentProvider>? {
        return contentProviderInjector
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>? {
        return supportFragmentInjector
    }

    object AppContext : ContextWrapper(instance)

    override fun onCreate() {
        super.onCreate()
        initRouter()
        initDi()
        initLogger()
        initCrash()
    }
    private fun initLogger() {
        val config = LogUtils.getConfig()
                .setLogSwitch(AppUtils.isAppDebug())// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(AppUtils.isAppDebug())// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setLogHeadSwitch(true)// 设置 log 头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印 log 时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-yyyy-MM-dd$fileExtension"
                .setFileExtension(".log")// 设置日志文件后缀
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setSingleTagSwitch(true)// 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setConsoleFilter(LogUtils.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtils.V)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(1)// log 栈深度，默认为 1
                .setStackOffset(1)// 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                .setSaveDays(3)// 设置日志可保留天数，默认为 -1 表示无限时长
                // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
//                .addFormatter<ArrayList<*>>(object : LogUtils.IFormatter<ArrayList<*>>() {
//                    override fun format(arrayList: ArrayList<*>): String {
//                        return "LogUtils Formatter ArrayList { $arrayList }"
//                    }
//                })
                .setFileWriter(null)
        LogUtils.i(config.toString())
    }

    private fun initCrash() {
        CrashUtils.init { crashInfo, error ->
            LogUtils.e(crashInfo)
            AppUtils.relaunchApp()
        }
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
        return AppModule()
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