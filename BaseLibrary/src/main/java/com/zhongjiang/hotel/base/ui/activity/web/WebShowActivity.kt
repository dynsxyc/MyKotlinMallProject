package com.zhongjiang.hotel.base.ui.activity.web

import android.graphics.Bitmap
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.gyf.barlibrary.ImmersionBar
import com.jakewharton.rxbinding2.view.RxView
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebConfig
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.PermissionInterceptor
import com.zhongjiang.youxuan.base.BuildConfig
import com.zhongjiang.youxuan.base.R
import com.zhongjiang.youxuan.base.common.UIController
import com.zhongjiang.youxuan.base.common.webclient.AgentWebSettings
import com.zhongjiang.youxuan.base.common.webclient.MiddlewareChromeClient
import com.zhongjiang.youxuan.base.common.webclient.MiddlewareWebViewClient
import com.zhongjiang.youxuan.base.ext.shieldDoubleClick
import com.zhongjiang.youxuan.base.ui.activity.BaseMvpActivity
import com.zhongjiang.youxuan.base.utils.ULogger
import kotlinx.android.synthetic.main.activity_webshow.*
import java.util.*
import javax.inject.Inject

@Route(path = "/baseui/webshow")
class WebShowActivity : BaseMvpActivity<WebShowActivityPresenter, WebShowActivityModel>(), WebShowActivityContract.View {

    @Autowired(name = "webUrl")
    @JvmField
    var webUrl: String = "http://www.jd.com"

    val mGson by lazy {
        Gson()
    }

    lateinit var mAgentWeb: AgentWeb

    @Inject
    lateinit var mAgentWebSettings: AgentWebSettings
    @Inject
    lateinit var mMiddlewareChromeClient: MiddlewareChromeClient
    @Inject
    lateinit var mMiddlewareWebViewClient: MiddlewareWebViewClient

    var mPermissionInterceptor = PermissionInterceptor { url, permissions, action ->
        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        ULogger.i("mUrl:" + url + "  permission:" + mGson.toJson(permissions) + " action:" + action)
        false
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_webshow
    }

    override fun initData() {
    }

    override fun initView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebShowActivityContent, -1, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setAgentWebWebSettings(mAgentWebSettings)//设置 IAgentWebSettings。
                .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setAgentWebUIController(UIController(this)) //自定义UI  AgentWeb3.0.0 加入。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .useMiddlewareWebChrome(mMiddlewareChromeClient) //设置WebChromeClient中间件，支持多个WebChromeClient，AgentWeb 3.0.0 加入。
                .useMiddlewareWebClient(mMiddlewareWebViewClient) //设置WebViewClient中间件，支持多个WebViewClient， AgentWeb 3.0.0 加入。
//                .setDownloadListener(mDownloadListener) 4.0.0 删除该API//下载回调
//                .openParallelDownload()// 4.0.0删除该API 打开并行下载 , 默认串行下载。 请通过AgentWebDownloader#Extra实现并行下载
//                .setNotifyIcon(R.drawable.ic_file_download_black_24dp) 4.0.0删除该api //下载通知图标。4.0.0后的版本请通过AgentWebDownloader#Extra修改icon
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go(webUrl)//WebView载入该url地址的页面并显示。
        if (BuildConfig.DEBUG) AgentWebConfig.debug()

        initThisView()
        // AgentWeb 4.0 开始，删除该类以及删除相关的API
//        DefaultMsgConfig.DownloadMsgConfig mDownloadMsgConfig = mAgentWeb.getDefaultMsgConfig().getDownloadMsgConfig();
        //  mDownloadMsgConfig.setCancel("放弃");  // 修改下载提示信息，这里可以语言切换

        // AgentWeb 没有把WebView的功能全面覆盖 ，所以某些设置 AgentWeb 没有提供 ， 请从WebView方面入手设置。
        mAgentWeb.webCreator.webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        //mAgentWeb.getWebCreator().getWebView()  获取WebView .
    }
    override fun injectRouter(): Boolean {
        return true
    }

    private fun initThisView() {
        getTopView()
        mMainToolbarRvTvBack?.let {
            RxView.clicks(it).shieldDoubleClick {
                onBackPressedSupport()
            }
        }
        mMainToolbarRvTvFinish?.let {
            RxView.clicks(it).shieldDoubleClick {
                finish()
            }
        }
        pageNavigator(View.GONE)

    }


    private var mWebViewClient = object : WebViewClient() {

        private val timer = HashMap<String, Long>()
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            ULogger.i("mUrl:$url onPageStarted  target:$webUrl")
            timer[url] = System.currentTimeMillis()
            if (url == webUrl) {
                pageNavigator(View.GONE)
            } else {
                pageNavigator(View.VISIBLE)
            }

        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

            if (timer[url] != null) {
                val overTime = System.currentTimeMillis()
                val startTime = timer[url]
                ULogger.i("  page mUrl:" + url + "  used time:" + (overTime - startTime!!))
            }

        }
        /*错误页回调该方法 ， 如果重写了该方法， 上面传入了布局将不会显示 ， 交由开发者实现，注意参数对齐。*/
        //	    public void onMainFrameError(AbsAgentWebUIController agentWebUIController, WebView mView, int errorCode, String description, String failingUrl) {
        //
        //            Log.i(TAG, "AgentWebFragment onMainFrameError");
        //            agentWebUIController.onMainFrameError(mView,errorCode,description,failingUrl);
        //
        //        }

        override fun onReceivedHttpError(view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse) {
            super.onReceivedHttpError(view, request, errorResponse)

            ULogger.i("onReceivedHttpError:" + 3 + "  request:" + mGson.toJson(request) + "  errorResponse:" + mGson.toJson(errorResponse));
        }
    }

    var mWebChromeClient =
            object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    //  super.onProgressChanged(mView, newProgress);
                    ULogger.i("onProgressChanged:$newProgress  mView:$view")
                }

                override fun onReceivedTitle(view: WebView, title: String) {
                    var titleStr = title
                    super.onReceivedTitle(view, titleStr)
                    if (mMainToolbarTvTitle != null && !TextUtils.isEmpty(titleStr)) {
                        if (titleStr.length > 10) {
                            titleStr = titleStr.substring(0, 10) + "..."
                        }
                    }
                    mMainToolbarTvTitle?.text = titleStr
                }
            }

    fun pageNavigator(tag: Int) {
        mMainToolbarRvTvBack?.visibility = tag
        mMainToolbarViewLine?.visibility = tag
        mMainToolbarRvTvFinish?.visibility = View.VISIBLE
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).fitsSystemWindows(true).init()
    }
}