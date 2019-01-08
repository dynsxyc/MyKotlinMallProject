package com.zhongjiang.kotlin.base.utils


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider


/**
 * See the detail [AutoDispose]
 */
class RxLifecycleUtils private constructor() {

    companion object {
        fun  bindLifecycle(lifecycleOwner: LifecycleOwner): ScopeProvider {
            return AndroidLifecycleScopeProvider.from(lifecycleOwner)
        }
        fun  bindBusLifecycle(lifecycleOwner: LifecycleOwner): ScopeProvider {
            return AndroidLifecycleScopeProvider.from(lifecycleOwner,Lifecycle.Event.ON_DESTROY)
        }
    }
}
