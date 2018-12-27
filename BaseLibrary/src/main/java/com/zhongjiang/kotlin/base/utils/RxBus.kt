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
    fun <T> toObservable(eventType: Class<T>,scopeProvider: ScopeProvider, onNext: Consumer<T>): Disposable {
        return register(eventType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).autoDisposable(scopeProvider)
                .subscribe(onNext);
    }
    /**
     * 订阅在io线程  观察在子线程
     * 处理onNext onError
     * */
    fun <T> toObservable(
            eventType: Class<T>,
            onNext: Consumer<T>,
            onError: Consumer<Throwable>): Disposable {
        return register(eventType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    fun <T> toObservable(
            eventType: Class<T>,
            onNext: Consumer<T>,
            onError: Consumer<Throwable>,
            onComplete: Action): Disposable {
        return register(eventType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError, onComplete);
    }

    fun <T> toObservable(
            eventType: Class<T>,
            onNext: Consumer<T>,
            onError: Consumer<Throwable>,
            onComplete: Action,
            onSubscribe: Consumer<Disposable>): Disposable {
        return register(eventType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError, onComplete, onSubscribe);
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
            onError: Consumer<Throwable>): Disposable {
        return register(eventType)
                .subscribeOn(subScheduler)
                .observeOn(obsScheduler)
                .subscribe(onNext, onError);
    }
    /**
     * 自定义线程
     * 只处理onNext
     * */
    fun <T> toObservable(
            eventType: Class<T>,
            subScheduler: Scheduler,
            obsScheduler: Scheduler,
            onNext: Consumer<T>): Disposable {
        return register(eventType).subscribeOn(subScheduler).observeOn(obsScheduler).subscribe(onNext);
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
            onComplete: Action): Disposable {
        return register(eventType)
                .subscribeOn(subScheduler)
                .observeOn(obsScheduler)
                .subscribe(onNext, onError, onComplete);
    }

    /**
     * 自定义线程
     * 处理onNext onError onComplete onSubscribe
     * */
    fun <T> toObservable(
            eventType: Class<T>,
            subScheduler: Scheduler,
            obsScheduler: Scheduler,
            onNext: Consumer<T>,
            onError: Consumer<Throwable>,
            onComplete: Action,
            onSubscribe: Consumer<Disposable>): Disposable {
        return register(eventType)
                .subscribeOn(subScheduler)
                .observeOn(obsScheduler)
                .subscribe(onNext, onError, onComplete, onSubscribe);
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