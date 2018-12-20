package com.zhongjiang.kotlin.base.utils


import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider


/**
 * See the detail [AutoDispose]
 */
class RxLifecycleUtils private constructor() {

    companion object {

        fun <T> bindLifecycle(lifecycleOwner: LifecycleOwner): AutoDisposeConverter<T> {
            return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner))
        }
    }
}
