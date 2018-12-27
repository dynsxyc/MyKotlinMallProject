package com.zhongjiang.kotlin.base.utils

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RxBus @Inject constructor() {
    var rxBus: Relay<Any> = BehaviorRelay.create()

    fun post(event: Any) {
        rxBus.accept(event);
    }

    fun <T> register(eventType: Class<T>): Observable<T> {
        return rxBus.ofType(eventType)
    }

    /**
     * 订阅在io线程  观察在子线程
     * 只处理onNext
     * */
    fun <T> toObservable(eventType: Class<T>, scopeProvider: ScopeProvider, onNext: Consumer<T>,provider: ScopeProvider): Disposable {
        return toObservable(eventType,Schedulers.io(),AndroidSchedulers.mainThread(),onNext,null   ,null,null,provider)
    }

    /**
     * 订阅在io线程  观察在子线程
     * 处理onNext onError
     * */
    fun <T> toObservable(
            eventType: Class<T>,
            onNext: Consumer<T>,
            onError: Consumer<Throwable>,provider: ScopeProvider): Disposable {
        return toObservable(eventType,Schedulers.io(),AndroidSchedulers.mainThread(),onNext,onError,null,null,provider)
    }

    fun <T> toObservable(
            eventType: Class<T>,
            onNext: Consumer<T>,
            onError: Consumer<Throwable>,
            onComplete: Action,provider: ScopeProvider): Disposable {
        return toObservable(eventType,Schedulers.io(),AndroidSchedulers.mainThread(),onNext,onError,onComplete,null,provider)
    }

    fun <T> toObservable(
            eventType: Class<T>,
            onNext: Consumer<T>,
            onError: Consumer<Throwable>,
            onComplete: Action,
            onSubscribe: Consumer<Disposable>,provider: ScopeProvider): Disposable {
        return toObservable(eventType,Schedulers.io(),AndroidSchedulers.mainThread(),onNext,onError,onComplete,onSubscribe,provider)
    }

    /**
     * 自定义线程
     * 处理onNext onError
     * */
    fun <T> toObservable(
            eventType: Class<T>,
            subScheduler: Scheduler,
            obsScheduler: Scheduler,
            onNext: Consumer<T>,
            onError: Consumer<Throwable>,provider: ScopeProvider): Disposable {
        return toObservable(eventType,subScheduler,obsScheduler,onNext,onError,null,null,provider)
    }

    /**
     * 自定义线程
     * 只处理onNext
     * */
    fun <T> toObservable(
            eventType: Class<T>,
            subScheduler: Scheduler,
            obsScheduler: Scheduler,
            onNext: Consumer<T>,provider: ScopeProvider): Disposable {
        return toObservable(eventType,subScheduler,obsScheduler,onNext,null,null,null,provider)
    }

    /**
     * 自定义线程
     * 处理onNext onError onComplete
     * */
    fun <T> toObservable(
            eventType: Class<T>,
            subScheduler: Scheduler,
            obsScheduler: Scheduler,
            onNext: Consumer<T>,
            onError: Consumer<Throwable>,
            onComplete: Action,provider: ScopeProvider): Disposable {
        return toObservable(eventType,subScheduler,obsScheduler,onNext,onError,onComplete,null,provider)
    }

    /**
     * 自定义线程
     * 处理onNext onError onComplete onSubscribe
     * */
    fun <T> toObservable(
            eventType: Class<T>,
            subScheduler: Scheduler,
            obsScheduler: Scheduler,
            onNext: Consumer<T>?,
            onError: Consumer<Throwable>?,
            onComplete: Action?,
            onSubscribe: Consumer<Disposable>?,provider: ScopeProvider): Disposable {
        var observable = register(eventType)
                .subscribeOn(subScheduler)
                .observeOn(obsScheduler).autoDisposable(provider)
        var disposable: Disposable
        if (onNext != null && onError != null && onComplete != null && onSubscribe != null) {
            disposable = observable.subscribe(onNext, onError, onComplete, onSubscribe)
        } else if (onNext != null && onError != null && onComplete != null ) {
            disposable = observable.subscribe(onNext, onError, onComplete)
        } else if (onNext != null && onError != null ) {
            disposable = observable.subscribe(onNext, onError)
        } else if (onNext != null ) {
            disposable = observable.subscribe(onNext)
        }  else {
            disposable = observable.subscribe()
        }
        return disposable
    }

    fun isObserver(): Boolean {
        return rxBus.hasObservers();
    }

    fun unregister(disposable: Disposable) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose();
        }
    }
}