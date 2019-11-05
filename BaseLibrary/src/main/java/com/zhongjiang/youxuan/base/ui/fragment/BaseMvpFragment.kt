package com.zhongjiang.youxuan.base.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import com.zhongjiang.youxuan.base.presenter.*
import com.zhongjiang.youxuan.base.widgets.ProgressLoading
import org.jetbrains.anko.toast
import java.lang.reflect.ParameterizedType
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

/**
 * Created by dyn on 2018/7/13.
 */
abstract class BaseMvpFragment<P : BasePresenter<BaseMvpFragment<P,M>,M>,M:IModel> : IView<P>, BaseInjectFragment() {
    @Inject
    override lateinit var mPresenter: P

    init {
        mPresenter.mView = this@BaseMvpFragment
    }

    private fun createPresenterKt():P {
        sequence {
            var thisClass:KClass<*> = this@BaseMvpFragment::class
            while (true){
                yield(thisClass.supertypes)
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure?:break
            }
        }.flatMap{
            it.flatMap{
                it.arguments
            }.asSequence()
        }.first{
            it.type?.jvmErasure?.isSubclassOf(IPresenter::class)?:false
        }.let{
            return it.type!!.jvmErasure.primaryConstructor!!.call() as P
        }
    }
    /**
     * 以下是java写法
     * */
    private fun createPresenterJava():P {
        sequence {
            var thisClass:Class<*> = this@BaseMvpFragment.javaClass
            while (true){
                yield(thisClass.genericSuperclass)
                thisClass = thisClass.superclass?:break
            }
        }.filter{
            it is ParameterizedType
        }.flatMap{
            (it as ParameterizedType).actualTypeArguments.asSequence()
        }.first{
            it is Class<*> && IPresenter::class.java.isAssignableFrom(it)
        }.let{
            return (it as Class<P>).newInstance()
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(getLayoutRes(), container, false)
        initLifecycleObserver(lifecycle)
        return attachToSwipeBack(rootView)
    }


    protected abstract fun initView()

    protected abstract fun initData()

    protected abstract fun getLayoutRes(): Int

    @CallSuper
    @MainThread
    protected fun initLifecycleObserver(lifecycle: Lifecycle) {
        mPresenter.setLifecycleOwner(this)
        lifecycle.addObserver(mPresenter)
    }

    private val progressLoading by lazy {
        ProgressLoading.create(_mActivity)
    }

    @MainThread
    override fun showLoading() {
        progressLoading.showLoading()
    }
    protected fun showCancelableLoading(){
        progressLoading.showCancelableLoading()
    }

    @MainThread
    override fun onError(status:Int,text: String) {
        _mActivity.toast(text)
    }

    @MainThread
    override fun hideLoading() {
        progressLoading.hideLoading()
    }

    override fun getHostActivity(): Activity {
        return _mActivity
    }

    override fun showToast(message: String) {

    }

    override fun showToast(resId: Int) {

    }

}