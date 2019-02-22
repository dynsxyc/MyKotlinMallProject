package com.zhongjiang.kotlin.base.utils


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException


/**
 * See the detail [AutoDispose]
 */
class RxLifecycleUtils private constructor() {

    companion object {
        fun bindLifecycle(lifecycleOwner: LifecycleOwner): ScopeProvider {
            return AndroidLifecycleScopeProvider.from(lifecycleOwner)
        }

        fun bindBusLifecycle(lifecycleOwner: LifecycleOwner): ScopeProvider {
            return AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)
        }

        private val DEFAULT_CORRESPONDING_EVENTS = CorrespondingEventsFunction<Lifecycle.Event> { lastEvent ->
            when (lastEvent) {
                Lifecycle.Event.ON_CREATE ->  Lifecycle.Event.ON_DESTROY
                Lifecycle.Event.ON_START ->  Lifecycle.Event.ON_STOP
                Lifecycle.Event.ON_RESUME ->  Lifecycle.Event.ON_PAUSE
                Lifecycle.Event.ON_PAUSE ->  Lifecycle.Event.ON_STOP
                Lifecycle.Event.ON_STOP, Lifecycle.Event.ON_DESTROY -> throw LifecycleEndedException("Lifecycle has ended! Last event was $lastEvent")
                else -> throw LifecycleEndedException("Lifecycle has ended! Last event was $lastEvent")
            }
        }

    }
}
