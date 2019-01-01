package com.zhongjiang.kotlin.base.common.webclient

import android.util.Log
import android.webkit.JsResult
import android.webkit.WebView

import com.just.agentweb.MiddlewareWebChromeBase

/**
 * Created by cenxiaozhong on 2017/12/16.
 * After agentweb 3.0.0  ， allow dev to custom self WebChromeClient's MiddleWare  .
 */
open class MiddlewareChromeClient : MiddlewareWebChromeBase() {
    override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
        Log.i("Info", "onJsAlert:$url")
        return super.onJsAlert(view, url, message, result)
    }

    override fun onProgressChanged(view: WebView, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        Log.i("Info", "onProgressChanged:")
    }
}
