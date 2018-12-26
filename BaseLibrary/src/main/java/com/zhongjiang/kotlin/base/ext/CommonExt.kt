@file:Suppress("UNREACHABLE_CODE")

package com.zhongjiang.kotlin.base.ext

import android.view.View
import android.widget.Button
import android.widget.EditText
import com.flyco.roundview.RoundTextView
import com.kotlin.base.widgets.DefaultTextWatcher
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import com.zhongjiang.kotlin.base.data.protocol.BaseListResp
import com.zhongjiang.kotlin.base.data.protocol.BaseResp
import com.zhongjiang.kotlin.base.injection.module.sheduler.SchedulerProvider
import com.zhongjiang.kotlin.base.rx.BaseFunc
import com.zhongjiang.kotlin.base.rx.BaseFuncBoolean
import com.zhongjiang.kotlin.base.rx.BaseFuncList
import com.zhongjiang.kotlin.base.rx.BaseMaybeObserver
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by dyn on 2018/7/13.
 * 扩展
 */
fun <T> Maybe<T>.excute(scopeProvider: ScopeProvider,observer: BaseMaybeObserver<T>){
    this.autoDisposable(scopeProvider).subscribe(observer)
}

fun <T> Maybe<T>.handlerThread(schedulers: SchedulerProvider): Maybe<T> {
    return this.subscribeOn(schedulers.io()).observeOn(schedulers.ui());
}

fun <T> Flowable<T>.handlerThread(schedulers: SchedulerProvider): Flowable<T> {
    return this.subscribeOn(schedulers.io()).observeOn(schedulers.ui());
}

fun <T> Maybe<BaseResp<T>>.convert(): Maybe<T> {
    return this.flatMap(BaseFunc())
}

fun <T> Maybe<BaseListResp<T>>.convertList(): Maybe<T> {
    return this.flatMap(BaseFuncList())
}

fun <T> Maybe<BaseResp<T>>.convertBoolean(): Maybe<Boolean> {
    return this.flatMap(BaseFuncBoolean())
}

fun View.OnClick(listener: View.OnClickListener) {
    this.setOnClickListener(listener)
}

fun View.OnClick(method: () -> Unit) {
    this.setOnClickListener { method() }
}

fun Button.editEmptyEnable(et: EditText, method: () -> Boolean) {
    val button = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            button.isEnabled = method()
        }
    })
}

fun Observable<Any>.shieldDoubleClick(method: () -> Unit) {
    this.throttleFirst(800, TimeUnit.MILLISECONDS)
            .subscribe {
                method()
            }
}

fun RoundTextView.editEnable(et: EditText, method: () -> Boolean){
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            this@editEnable.isClickable = method()
            this@editEnable.isEnabled = method()
        }
    })
}

/*
    扩展视图可见性
 */
fun View.setVisible(visible:Boolean){
    this.visibility = if (visible) View.VISIBLE else View.GONE
}