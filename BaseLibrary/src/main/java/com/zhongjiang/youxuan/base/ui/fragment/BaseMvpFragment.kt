package com.zhongjiang.youxuan.base.ui.fragment

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
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmErasure

/**
 * Created by dyn on 2018/7/13.
 */
abstract class BaseMvpFragment<out P : BasePresenter<BaseMvpFragment<P,M>,M>,M:IModel> : IView<P>, BaseInjectFragment() {

    override val presenter: P

    init {
        presenter = createPresenterKt()
        presenter.view = this
    }

    fun createPresenterKt():P {
        buildSequence {
          var thisClass:KClass<*> = this@BaseMvpFragment::class
            while (true){
                yield(thisClass.supertypes)
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure?:break
            }
        }.flaMap{
            it.flaMap{
                it.arguments
            }.asSequence()
        }.first{
            it.type?jvmErasure?.isSubclassOf(IPresenter::class)?:false
        }.let{
            return it.type!!.jvmErasure.primaryConstructor!!.call() as P
        }
    }

    fun create

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
        presenter.setLifecycleOwner(this)
        lifecycle.addObserver(presenter)
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

}