package com.zhongjiang.hotel.base.utils

import com.jakewharton.rxrelay2.PublishRelay
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
    companion object {
        /**
         * 有以下三种relay 类型
         * I   BehaviorRelay 实例：
         *      relay 接收全部事件
         *     relay.subscribe(observer);
               relay.accept("one");
                relay.accept("two");
                relay.accept("three");
                relay 接收到订阅前的最后一个事件 和订阅后的所有事件
                relay.accept("zero");
                relay.accept("one");
                relay.subscribe(observer);
                relay.accept("two");
                relay.accept("three");
         II   PublishRelay 实例：
                // 接收到订阅后的所有事件，接收不到订阅前的事件
                relay.subscribe(observer1);
                relay.accept("one");
                relay.accept("two");
                // observer2 will only receive "three"
                relay.subscribe(observer2);
                relay.accept("three");
         III  ReplayRelay实例
              不论是先订阅还是后订阅 ，都能接收到所有的事件

         * */
        private var rxBus: Relay<Any> = PublishRelay.create()
    }

    fun post(event: Any) {
        rxBus.accept(event)
    }

    private fun <T> register(eventType: Class<T>): Observable<T> {
        return rxBus.ofType(eventType)
    }

    /**
     * 订阅在io线程  观察在子线程
     * 只处理onNext
     * */
    fun <T> toObservable(eventType: Class<T>, onNext: Consumer<T>,provider: ScopeProvider): Disposable {
        return toObservable(eventType,Schedulers.io(),AndroidSchedulers.mainThread(),onNext,null   ,null,null,provider)
    }

    /**
     * 订阅在io线程  观察在子线程
     * 处理onNext onNetError
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
     * 处理onNext onNetError
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
     * 处理onNext onNetError onComplete
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
     * 处理onNext onNetError onComplete onSubscribe
     * */
    fun <T> toObservable(
            eventType: Class<T>,
            subScheduler: Scheduler,
            obsScheduler: Scheduler,
            onNext: Consumer<T>?,
            onError: Consumer<Throwable>?,
            onComplete: Action?,
            onSubscribe: Consumer<Disposable>?,provider: ScopeProvider): Disposable {
        val observable = register(eventType)
                .subscribeOn(subScheduler)
                .observeOn(obsScheduler).autoDisposable(provider)
        return if (onNext != null && onError != null && onComplete != null && onSubscribe != null) {
            observable.subscribe(onNext, onError, onComplete, onSubscribe)
        } else if (onNext != null && onError != null && onComplete != null ) {
            observable.subscribe(onNext, onError, onComplete)
        } else if (onNext != null && onError != null ) {
            observable.subscribe(onNext, onError)
        } else if (onNext != null ) {
            observable.subscribe(onNext)
        }  else {
            observable.subscribe()
        }
    }

    fun isObserver(): Boolean {
        return rxBus.hasObservers()
    }

    fun unregister(disposable: Disposable) {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }
}