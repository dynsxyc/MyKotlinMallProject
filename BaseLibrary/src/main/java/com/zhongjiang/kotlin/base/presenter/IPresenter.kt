package com.zhongjiang.kotlin.base.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.jetbrains.annotations.NotNull

interface IPresenter : LifecycleObserver {
    fun setLifecycleOwner(lifecycleOwner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(@NotNull owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(@NotNull owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(@NotNull owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(@NotNull owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(@NotNull owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(@NotNull owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onLifecycleChanged(@NotNull owner: LifecycleOwner)
}