package com.zhongjiang.hotel.base.ui.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.*
import com.google.gson.Gson
import com.jakewharton.rxbinding2.view.RxView
import com.just.agentweb.*
import com.zhongjiang.youxuan.base.BuildConfig
import com.zhongjiang.youxuan.base.R
import com.zhongjiang.youxuan.base.common.UIController
import com.zhongjiang.youxuan.base.common.webclient.AgentWebSettings
import com.zhongjiang.youxuan.base.common.webclient.MiddlewareChromeClient
import com.zhongjiang.youxuan.base.common.webclient.MiddlewareWebViewClient
import com.zhongjiang.youxuan.base.ext.shieldDoubleClick
import kotlinx.android.synthetic.main.activity_webshow.*
import me.yokeyword.fragmentation.SwipeBackLayout
import me.yokeyword.fragmentation.SwipeBackLayout.*
import java.util.*

class YXWebShowFragment : BaseInjectFragment() {
    companion object {
        lateinit var mAgentWeb: AgentWeb
        const val URL_KEY = "url_key"
        val mGson by lazy {
            Gson()
        }
        val TAG = "YXWebShowFragment"
        fun newInstance(netUrl: String): YXWebShowFragment {
            val mYXWebShowFragment = YXWebShowFragment()
            val bundle = Bundle()
            bundle.putString(URL_KEY, netUrl)
            return mYXWebShowFragment
        }

        var mPermissionInterceptor = PermissionInterceptor { url, permissions, action ->
            /**
             * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
             * @param url
             * @param permissions
             * @param action
             * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
             */
            Log.i(TAG, "mUrl:" + url + "  permission:" + mGson.toJson(permissions) + " action:" + action)
            false
        }

    }

    private var mWebViewClient = object : WebViewClient() {

        private val timer = HashMap<String, Long>()
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.i(TAG, "mUrl:" + url + " onPageStarted  target:" + getUrl())
            timer[url] = System.currentTimeMillis()
            if (url == getUrl()) {
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
                Log.i(TAG, "  page mUrl:" + url + "  used time:" + (overTime - startTime!!))
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

            //			Log.i(TAG, "onReceivedHttpError:" + 3 + "  request:" + mGson.toJson(request) + "  errorResponse:" + mGson.toJson(errorResponse));
        }
    }

    var mWebChromeClient =
            object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    //  super.onProgressChanged(mView, newProgress);
                    Log.i(TAG, "onProgressChanged:$newProgress  mView:$view")
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return attachToSwipeBack(inflater.inflate(R.layout.activity_webshow, container, false))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebShowActivityContent, -1, ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT))
                .useDefaultIndicator()//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setAgentWebUIController(UIController(mActivity)) //自定义UI  AgentWeb3.0.0 加入。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .useMiddlewareWebChrome(getMiddlewareWebChrome()) //设置WebChromeClient中间件，支持多个WebChromeClient，AgentWeb 3.0.0 加入。
                .useMiddlewareWebClient(getMiddlewareWebClient()) //设置WebViewClient中间件，支持多个WebViewClient， AgentWeb 3.0.0 加入。
//                .setDownloadListener(mDownloadListener) 4.0.0 删除该API//下载回调
//                .openParallelDownload()// 4.0.0删除该API 打开并行下载 , 默认串行下载。 请通过AgentWebDownloader#Extra实现并行下载
//                .setNotifyIcon(R.drawable.ic_file_download_black_24dp) 4.0.0删除该api //下载通知图标。4.0.0后的版本请通过AgentWebDownloader#Extra修改icon
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go(getUrl())//WebView载入该url地址的页面并显示。
        if (BuildConfig.DEBUG) AgentWebConfig.debug()

        initView()
        // AgentWeb 4.0 开始，删除该类以及删除相关的API
//        DefaultMsgConfig.DownloadMsgConfig mDownloadMsgConfig = mAgentWeb.getDefaultMsgConfig().getDownloadMsgConfig();
        //  mDownloadMsgConfig.setCancel("放弃");  // 修改下载提示信息，这里可以语言切换

        // AgentWeb 没有把WebView的功能全面覆盖 ，所以某些设置 AgentWeb 没有提供 ， 请从WebView方面入手设置。
        mAgentWeb.webCreator.webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        //mAgentWeb.getWebCreator().getWebView()  获取WebView .


    }

    protected fun initView() {
        view?.let { getTopView(it) }
        mMainToolbarRvTvBack?.let {
            RxView.clicks(it).shieldDoubleClick {
            onBackPressedSupport()
        }
        }
        mMainToolbarRvTvFinish?.let {
            RxView.clicks(it).shieldDoubleClick {
            pop()
        }
        }
        mMainToolbarRvTvLastRight?.let {
            RxView.clicks(it).shieldDoubleClick {

        }
        }
        pageNavigator(View.GONE)

        swipeBackLayout.addSwipeListener(object : SwipeBackLayout.OnSwipeListener{
            override fun onEdgeTouch(oritentationEdgeFlag: Int) {

            }

            override fun onDragScrolled(scrollPercent: Float) {
                Log.i("test1","百分比 $scrollPercent")
            }

            override fun onDragStateChange(state: Int) {
                /** @see #STATE_IDLE
                * @see #STATE_DRAGGING
                * @see #STATE_SETTLING
                * @see #STATE_FINISHED*/
                when(state){
                    STATE_IDLE -> Log.i("test1","STATE_IDLE")
                    STATE_DRAGGING -> Log.i("test1","STATE_DRAGGING")
                    STATE_SETTLING -> Log.i("test1","STATE_SETTLING")
                    STATE_FINISHED -> Log.i("test1","STATE_FINISHED")
                }
            }

        })
    }

    override fun onBackPressedSupport(): Boolean {
        // true表示AgentWeb处理了该事件
        if (!mAgentWeb.back()) {
            pop()
        }
        return true
    }

    fun pageNavigator(tag: Int) {
        mMainToolbarRvTvBack?.visibility = tag
        mMainToolbarViewLine?.visibility = tag
        mMainToolbarRvTvFinish?.visibility = View.VISIBLE
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume() //恢复
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()  //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
        super.onPause()
    }

    override fun onDestroyView() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroyView()
    }

    /**
     * MiddlewareWebClientBase 是 AgentWeb 3.0.0 提供一个强大的功能，
     * 如果用户需要使用 AgentWeb 提供的功能， 不想重写 WebClientView方
     * 法覆盖AgentWeb提供的功能，那么 MiddlewareWebClientBase 是一个
     * 不错的选择 。
     *
     * @return
     */
    protected fun getMiddlewareWebClient(): MiddlewareWebClientBase {
        return object : MiddlewareWebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }

    protected fun getMiddlewareWebChrome(): MiddlewareWebChromeBase {
        return object : MiddlewareChromeClient() {

        }
    }

    /**
     * 页面空白，请检查scheme是否加上， scheme://host:port/path?query&query 。
     *
     * @return mUrl
     */
    fun getUrl(): String? {
        var resultUrl = arguments?.getString(URL_KEY)
        if (TextUtils.isEmpty(resultUrl))
            resultUrl = "http://www.jd.com"
        return resultUrl
    }


    /**
     * @return IAgentWebSettings
     */
    fun getSettings(): IAgentWebSettings<WebSettings> {
        return AgentWebSettings()
    }

    override fun getSwipeBackEnable(): Boolean {
        return false
    }

}