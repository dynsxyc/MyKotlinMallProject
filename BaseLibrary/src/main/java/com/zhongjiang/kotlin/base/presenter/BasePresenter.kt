package com.zhongjiang.kotlin.base.presenter

import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import com.orhanobut.logger.Logger
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import com.zhongjiang.kotlin.base.NetWorkUtils
import com.zhongjiang.kotlin.base.busevent.ActivityResultEvent
import com.zhongjiang.kotlin.base.busevent.LoginSuccessEvent
import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.data.db.UserInfoEntity
import com.zhongjiang.kotlin.base.ext.excute
import com.zhongjiang.kotlin.base.oss.OssService
import com.zhongjiang.kotlin.base.oss.UpFileBean
import com.zhongjiang.kotlin.base.utils.RxBus
import com.zhongjiang.kotlin.base.utils.RxLifecycleUtils
import io.objectbox.Box
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by dyn on 2018/7/13.
 */
open class BasePresenter<V : IView, M : IModel> constructor(view: V, model: M) : IPresenter {
    //    1. lateinit 延迟加载
//    2.lateinit 只能修饰, 非kotlin基本类型
//    因为Kotlin会使用null来对每一个用lateinit修饰的属性做初始化，而基础类型是没有null类型，所以无法使用lateinit。
    var mView = view

    var mModel = model
    @Inject
    lateinit var context: BaseApplication

    @field:Named("public")
    @Inject
    lateinit var publicOssService: OssService

    @field:Named("security")
    @Inject
    lateinit var securityOssService: OssService

    @Inject
    @Singleton
    lateinit var mRxBus: RxBus

    lateinit var timerDisposable: Disposable

    @Inject
    lateinit var mUserInfoEntity: Box<UserInfoEntity>

    fun checkNetWork(): Boolean {
        if (NetWorkUtils.isNetWorkAvailable(context)) {
            return true
        }
        return false
    }

    lateinit var lifecycleProvider: LifecycleOwner

    fun bindLifecycle(): ScopeProvider {
        return RxLifecycleUtils.bindLifecycle(lifecycleProvider)
    }

    fun bindBusLifecycle(): ScopeProvider {
        return RxLifecycleUtils.bindBusLifecycle(lifecycleProvider)
    }

    override fun setLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        this.lifecycleProvider = lifecycleOwner
    }

    @MainThread
    @CallSuper
    override fun onCreate(owner: LifecycleOwner) {
    }

    @MainThread
    @CallSuper
    override fun onStart(owner: LifecycleOwner) {
    }

    @MainThread
    @CallSuper
    override fun onResume(owner: LifecycleOwner) {
    }

    @MainThread
    @CallSuper
    override fun onPause(owner: LifecycleOwner) {
    }

    @MainThread
    @CallSuper
    override fun onStop(owner: LifecycleOwner) {
    }

    @CallSuper
    @MainThread
    override fun onDestroy(owner: LifecycleOwner) {
        mModel.onDestroy()
    }

    @CallSuper
    @MainThread
    override fun onLifecycleChanged(owner: LifecycleOwner) {
    }

    fun registerActivityResultEvent(method: (ActivityResultEvent) -> Unit) {
        mRxBus.toObservable(ActivityResultEvent::class.java, Consumer {
            method(it)
            Logger.i("registerActivityResultEvent   调用成功")
        }, Consumer {
            it.printStackTrace()
            Logger.e("registerActivityResultEvent   error")
            Logger.e("registerActivityResultEvent   $it")
        }, Action {
            Logger.i("registerActivityResultEvent   oncomplete")
        }, Consumer {
            Logger.i("registerActivityResultEvent  isDisposed =  ${it.isDisposed}")
        }, bindBusLifecycle())
    }
    /**开启一个定时器 1秒*/
    fun startTimmer(number:Long,onNext: Consumer<Long>, onComplete: Action):Disposable{
        timerDisposable = mModel.startTimer(number,onNext,onComplete).excute(bindBusLifecycle())
        return timerDisposable
    }
    /**关闭定时器*/
    fun stopTimeer(){
        timerDisposable?.let {
            if (it.isDisposed.not())
                it.dispose()
        }
    }
    fun onLoginSuccess(){
        mRxBus.post(LoginSuccessEvent())
    }

    fun upFile(upFileBean: UpFileBean,callback:(UpFileBean)->Unit){
        var ossService = publicOssService
        if (upFileBean.filemoduleType.isSecurity){
            ossService = securityOssService
        }
        if (upFileBean.isImage){
            Flowable.create(FlowableOnSubscribe<UpFileBean> {
                Luban.with(context).ignoreBy(500).filter {
                    it.isNotEmpty() or it.toLowerCase().endsWith(".gif")
                }.load(upFileBean.filePath).setCompressListener(object :OnCompressListener{
                    override fun onSuccess(file: File) {
                        upFileBean.filePath = file.absolutePath
                        it.onNext(upFileBean)
                    }

                    override fun onError(e: Throwable) {
                        it.onError(e)
                    }

                    override fun onStart() {

                    }

                }).launch()
            },BackpressureStrategy.BUFFER).subscribe({
                    ossService.asyncPutFile(it).autoDisposable(bindBusLifecycle()).subscribe({
                        callback(it)
                    }, {
                        upFileBean.upType = 3
                        callback(upFileBean)
                    })
                }, {
                    ossService.asyncPutFile(upFileBean).autoDisposable(bindBusLifecycle()).subscribe({
                        callback(it)
                    }, {
                        upFileBean.upType = 3
                        callback(upFileBean)
                    })

            })
        }else {
            ossService.asyncPutFile(upFileBean).autoDisposable(bindBusLifecycle()).subscribe({
                callback(it)
            }, {
                upFileBean.upType = 3
                callback(upFileBean)
            })
        }
    }
    /**
     * 多文件上传
     * */
    fun upFiles(list:List<UpFileBean>,callback:(UpFileBean)->Unit){
        Flowable.fromIterable(list).flatMap {
            upFile(it,callback)
            Flowable.empty<String>()
        }.subscribe()
    }
}