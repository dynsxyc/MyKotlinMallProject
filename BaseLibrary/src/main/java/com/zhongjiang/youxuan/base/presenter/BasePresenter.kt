package com.zhongjiang.youxuan.base.presenter

import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import com.orhanobut.logger.Logger
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import com.zhongjiang.youxuan.base.NetWorkUtils
import com.zhongjiang.youxuan.base.busevent.ActivityResultEvent
import com.zhongjiang.youxuan.base.busevent.LoginSuccessEvent
import com.zhongjiang.youxuan.base.common.BaseApplication
import com.zhongjiang.youxuan.base.data.db.UserInfoEntity
import com.zhongjiang.youxuan.base.ext.excute
import com.zhongjiang.youxuan.base.oss.OssService
import com.zhongjiang.youxuan.base.oss.UpFileBean
import com.zhongjiang.youxuan.base.utils.RxBus
import com.zhongjiang.youxuan.base.utils.RxLifecycleUtils
import io.objectbox.Box
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import org.reactivestreams.Subscription
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
    fun startTimmer(number: Long, onNext: Consumer<Long>, onComplete: Action): Disposable {
        timerDisposable = mModel.startTimer(number, onNext, onComplete).excute(bindBusLifecycle())
        return timerDisposable
    }

    /**关闭定时器*/
    fun stopTimeer() {
        timerDisposable.let {
            if (it.isDisposed.not())
                it.dispose()
        }
    }

    fun onLoginSuccess() {
        mRxBus.post(LoginSuccessEvent())
    }

    fun upFile(upFileBean: UpFileBean, callback: (UpFileBean) -> Unit): Disposable {
        var ossService = publicOssService
        if (upFileBean.filemoduleType.isSecurity) {
            ossService = securityOssService
        }
        var disposable =  ossService.asyncPutFile(upFileBean).autoDisposable(bindBusLifecycle()).subscribe({
            callback(it)
        }, {
            upFileBean.upType = 3
            callback(upFileBean)
            upFileBean.disposable?.let {
                if (!it.isDisposed){
                    it.dispose()
                }
            }
        }, {
            upFileBean.disposable?.let {
                if (!it.isDisposed){
                    Logger.i("-----------------------dispose")
                    it.dispose()
                }
            }
        })
        upFileBean.disposable = disposable
        return disposable
    }

    /**
     * 多文件上传
     * */
    fun upFiles(list: List<UpFileBean>, callback: (UpFileBean) -> Unit) {
        var subscription:Subscription?=null
        Flowable.fromIterable(list).subscribe({
            upFile(it) { it ->
                if (it.upType!=1 && subscription != null){
                    subscription!!.request(1)
                }
                callback(it)
            }
        },{
            subscription?.let {
                it.request(1)
            }
        },{

        },{
            subscription= it
            it.request(1)

        })
    }
}