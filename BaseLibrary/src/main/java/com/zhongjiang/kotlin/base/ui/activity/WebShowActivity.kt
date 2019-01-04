package com.zhongjiang.kotlin.base.ui.activity

import android.graphics.Bitmap
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.jakewharton.rxbinding2.view.RxView
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebConfig
import com.just.agentweb.DefaultWebClient
import com.zhongjiang.kotlin.base.BuildConfig
import com.zhongjiang.kotlin.base.R
import com.zhongjiang.kotlin.base.common.UIController
import com.zhongjiang.kotlin.base.common.webclient.AgentWebSettings
import com.zhongjiang.kotlin.base.common.webclient.MiddlewareChromeClient
import com.zhongjiang.kotlin.base.common.webclient.MiddlewareWebViewClient
import com.zhongjiang.kotlin.base.ext.shieldDoubleClick
import com.zhongjiang.kotlin.base.presenter.WebShowActivityPresenter
import com.zhongjiang.kotlin.base.presenter.webshowactivity.WebShowActivityContract
import com.zhongjiang.kotlin.base.ui.fragment.YXWebShowFragment
import com.zhongjiang.kotlin.base.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_webshow.*
import kotlinx.android.synthetic.main.toolbar_main.*
import me.yokeyword.fragmentation.SwipeBackLayout
import java.util.*
import javax.inject.Inject

@Route(path = "/baseui/webshow")
class WebShowActivity : BaseMvpActivity<WebShowActivityPresenter>(), WebShowActivityContract.View {

    @Autowired(name = "boolean")
    @JvmField
    var isSplash: Boolean = false
    @Autowired(name = "webUrl")
    @JvmField
    var webUrl: String = ""

    @Inject
    lateinit var mAgentWebSettings: AgentWebSettings
    @Inject
    lateinit var mMiddlewareChromeClient: MiddlewareChromeClient
    @Inject
    lateinit var mMiddlewareWebViewClient: MiddlewareWebViewClient

    override fun getLayoutId(): Int {
        return R.layout.activity_webshow
    }

    override fun initData() {
    }

    override fun initView() {
        YXWebShowFragment.mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebShowActivityContent, -1, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setAgentWebWebSettings(mAgentWebSettings)//设置 IAgentWebSettings。
                .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
                .setPermissionInterceptor(YXWebShowFragment.mPermissionInterceptor) //权限拦截 2.0.0 加入。
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
        YXWebShowFragment.mAgentWeb.webCreator.webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        //mAgentWeb.getWebCreator().getWebView()  获取WebView .
    }

    private fun initThisView() {
        StatusBarUtil.setMargin(mMainToolbarRl.context, mMainToolbarRl)
        RxView.clicks(mMainToolbarImgBack).shieldDoubleClick {
            onBackPressedSupport()
        }
        RxView.clicks(mMainToolbarImgFinish).shieldDoubleClick {
            pop()
        }
        RxView.clicks(mMainToolbarImgMore).shieldDoubleClick {

        }
        pageNavigator(View.GONE)

        swipeBackLayout.addSwipeListener(object : SwipeBackLayout.OnSwipeListener {
            override fun onEdgeTouch(oritentationEdgeFlag: Int) {

            }

            override fun onDragScrolled(scrollPercent: Float) {
                Log.i("test1", "百分比 $scrollPercent")
            }

            override fun onDragStateChange(state: Int) {
                /** @see #STATE_IDLE
                 * @see #STATE_DRAGGING
                 * @see #STATE_SETTLING
                 * @see #STATE_FINISHED*/
                when (state) {
                    SwipeBackLayout.STATE_IDLE -> Log.i("test1", "STATE_IDLE")
                    SwipeBackLayout.STATE_DRAGGING -> Log.i("test1", "STATE_DRAGGING")
                    SwipeBackLayout.STATE_SETTLING -> Log.i("test1", "STATE_SETTLING")
                    SwipeBackLayout.STATE_FINISHED -> Log.i("test1", "STATE_FINISHED")
                }
            }

        })
    }


    private var mWebViewClient = object : WebViewClient() {

        private val timer = HashMap<String, Long>()
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.i(YXWebShowFragment.TAG, "mUrl:" + url + " onPageStarted  target:" + webUrl)
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
                Log.i(YXWebShowFragment.TAG, "  page mUrl:" + url + "  used time:" + (overTime - startTime!!))
            }

        }
        /*错误页回调该方法 ， 如果重写了该方法， 上面传入了布局将不会显示 ， 交由开发者实现，注意参数对齐。*/
        //	    public void onMainFrameError(AbsAgentWebUIController agentWebUIController, WebView view, int errorCode, String description, String failingUrl) {
        //
        //            Log.i(TAG, "AgentWebFragment onMainFrameError");
        //            agentWebUIController.onMainFrameError(view,errorCode,description,failingUrl);
        //
        //        }

        override fun onReceivedHttpError(view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse) {
            super.onReceivedHttpError(view, request, errorResponse)

            Log.i(YXWebShowFragment.TAG, "onReceivedHttpError:" + 3 + "  request:" + YXWebShowFragment.mGson.toJson(request) + "  errorResponse:" + YXWebShowFragment.mGson.toJson(errorResponse));
        }
    }

    var mWebChromeClient =
            object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    //  super.onProgressChanged(view, newProgress);
                    Log.i(YXWebShowFragment.TAG, "onProgressChanged:$newProgress  view:$view")
                }

                override fun onReceivedTitle(view: WebView, title: String) {
                    var titleStr = title
                    super.onReceivedTitle(view, titleStr)
                    if (mMainToolbarTvTitle != null && !TextUtils.isEmpty(titleStr)) {
                        if (titleStr.length > 10) {
                            titleStr = titleStr.substring(0, 10) + "..."
                        }
                    }
                    mMainToolbarTvTitle.text = titleStr
                }
            }

    fun pageNavigator(tag: Int) {
        mMainToolbarImgBack.visibility = tag
        mMainToolbarViewLine.visibility = tag
        mMainToolbarImgFinish.visibility = View.VISIBLE
    }
}