package com.zhongjiang.youxuan.base.common.webclient

import android.app.Activity
import android.webkit.WebView
import com.just.agentweb.AbsAgentWebSettings
import com.just.agentweb.AgentWeb
import com.just.agentweb.LogUtils
import com.just.agentweb.WebListenerManager
import com.just.agentweb.download.AgentWebDownloader
import com.just.agentweb.download.DefaultDownloadImpl
import com.just.agentweb.download.DownloadListenerAdapter
import com.just.agentweb.download.DownloadingService
import com.zhongjiang.youxuan.base.R
import com.zhongjiang.youxuan.base.ui.fragment.YXWebShowFragment
import javax.inject.Inject

open class AgentWebSettings @Inject constructor() : AbsAgentWebSettings() {
    lateinit var magentWeb: AgentWeb

    override fun bindAgentWebSupport(agentWeb: AgentWeb) {
        this.magentWeb = agentWeb
    }

    /**
     * AgentWeb 4.0.0 内部删除了 DownloadListener 监听 ，以及相关API ，将 Download 部分完全抽离出来独立一个库，
     * 如果你需要使用 AgentWeb Download 部分 ， 请依赖上 compile 'com.just.agentweb:download:4.0.0 ，
     * 如果你需要监听下载结果，请自定义 AgentWebSetting ， New 出 DefaultDownloadImpl，传入DownloadListenerAdapter
     * 实现进度或者结果监听，例如下面这个例子，如果你不需要监听进度，或者下载结果，下面 setDownloader 的例子可以忽略。
     * @param webView
     * @param downloadListener
     * @return WebListenerManager
     */
    override fun setDownloader(webView: WebView, downloadListener: android.webkit.DownloadListener?): WebListenerManager {
        webView.setDownloadListener(
                DefaultDownloadImpl
                        .create(webView.context as Activity,
                                webView,
                                mDownloadListenerAdapter,
                                mDownloadListenerAdapter,
                                this.magentWeb.getPermissionInterceptor()))
        return this
    }




    /**
     * 更新于 AgentWeb  4.0.0
     */
    protected var mDownloadListenerAdapter: DownloadListenerAdapter = object : DownloadListenerAdapter() {

        /**
         *
         * @param url                下载链接
         * @param userAgent          UserAgent
         * @param contentDisposition ContentDisposition
         * @param mimetype           资源的媒体类型
         * @param contentLength      文件长度
         * @param extra              下载配置 ， 用户可以通过 Extra 修改下载icon ， 关闭进度条 ， 是否强制下载。
         * @return true 表示用户处理了该下载事件 ， false 交给 AgentWeb 下载
         */
        override fun onStart(url: String?, userAgent: String?, contentDisposition: String?, mimetype: String?, contentLength: Long, extra: AgentWebDownloader.Extra): Boolean {
            LogUtils.i(YXWebShowFragment.TAG, "onStart:" + url!!)
            extra.setOpenBreakPointDownload(true) // 是否开启断点续传
                    .setIcon(R.drawable.ic_file_download_black_24dp) //下载通知的icon
                    .setConnectTimeOut(6000) // 连接最大时长
                    .setBlockMaxTime(10 * 60 * 1000)  // 以8KB位单位，默认60s ，如果60s内无法从网络流中读满8KB数据，则抛出异常
                    .setDownloadTimeOut(java.lang.Long.MAX_VALUE) // 下载最大时长
                    .setParallelDownload(false)  // 串行下载更节省资源哦
                    .setEnableIndicator(true)  // false 关闭进度通知
                    .addHeader("Cookie", "xx") // 自定义请求头
                    .setAutoOpen(true).isForceDownload = true // 强制下载，不管网络网络类型
            return false
        }

        /**
         *
         * 不需要暂停或者停止下载该方法可以不必实现
         * @param url
         * @param downloadingService  用户可以通过 DownloadingService#shutdownNow 终止下载
         */
        override fun onBindService(url: String?, downloadingService: DownloadingService?) {
            super.onBindService(url, downloadingService)
//            mDownloadingService = downloadingService
            LogUtils.i(YXWebShowFragment.TAG, "onBindService:$url  DownloadingService:$downloadingService")
        }

        /**
         * 回调onUnbindService方法，让用户释放掉 DownloadingService。
         * @param url
         * @param downloadingService
         */
        override fun onUnbindService(url: String?, downloadingService: DownloadingService?) {
            super.onUnbindService(url, downloadingService)
//            mDownloadingService = null
            LogUtils.i(YXWebShowFragment.TAG, "onUnbindService:" + url!!)
        }

        /**
         *
         * @param url  下载链接
         * @param loaded  已经下载的长度
         * @param length    文件的总大小
         * @param usedTime   耗时 ，单位ms
         * 注意该方法回调在子线程 ，线程名 AsyncTask #XX 或者 AgentWeb # XX
         */
        override fun onProgress(url: String?, loaded: Long, length: Long, usedTime: Long) {
            val mProgress = (loaded / java.lang.Float.valueOf(length.toFloat()) * 100).toInt()
            LogUtils.i(YXWebShowFragment.TAG, "onProgress:$mProgress")
            super.onProgress(url, loaded, length, usedTime)
        }

        /**
         *
         * @param path 文件的绝对路径
         * @param url  下载地址
         * @param throwable    如果异常，返回给用户异常
         * @return true 表示用户处理了下载完成后续的事件 ，false 默认交给AgentWeb 处理
         */
        override fun onResult(path: String?, url: String?, throwable: Throwable?): Boolean {
            if (null == throwable) { //下载成功
                //do you work
            } else {//下载失败

            }
            return false // true  不会发出下载完成的通知 , 或者打开文件
        }
    }
}