package com.zhongjiang.net.service

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.zhongjiang.net.HttpRequestProvider
import com.zhongjiang.net.http.HttpRequest
import java.net.URI
import java.util.*
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class WorkStation(private val mHttpRequestProvider: HttpRequestProvider) {
    companion object {
        private const val MAX_REQUEST_SIZE = 60
        var index = AtomicInteger()
        val sThreadPool = ThreadPoolExecutor(0, Int.MAX_VALUE, 60, TimeUnit.SECONDS, SynchronousQueue<Runnable>(), ThreadFactory {
            val thread = Thread(it)
            thread.name = "http thread name is ${index.getAndIncrement()}"
            return@ThreadFactory thread
        })

    }
    var mRunning = ArrayDeque<IRequest>()
    var mCache = ArrayDeque<IRequest>()
    fun add(iRequest: IRequest){
        if (mRunning.size>MAX_REQUEST_SIZE){
            mCache.add(iRequest)
        }else{
            doHttpRequest(iRequest)
        }
    }

    private fun doHttpRequest(iRequest: IRequest) {
        Log.i("testNet" , "runnable  $iRequest ")
        sThreadPool.execute(HttpRunnable(mHttpRequestProvider.getHttpRequest(URI.create(iRequest.mUrl),iRequest.mMethod),iRequest,mWorkStation = this))
    }

    fun finish(request:IRequest){
        mRunning.remove(request)
        if (mRunning.size> MAX_REQUEST_SIZE){
            return
        }
        if (mCache.size == 0 ){
            return
        }
        var iterator  = mCache.iterator()
        while (iterator.hasNext()){
            val next = iterator.next()
            mRunning.add(next)
            iterator.remove()
            doHttpRequest(next)
        }

    }
}