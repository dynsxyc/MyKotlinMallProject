@file:Suppress("UNREACHABLE_CODE")

package com.zhongjiang.kotlin.base.ext

import android.view.View
import android.widget.Button
import android.widget.EditText
import com.kotlin.base.widgets.DefaultTextWatcher
import com.trello.rxlifecycle.LifecycleProvider
import com.zhongjiang.kotlin.base.data.protocol.BaseListResp
import com.zhongjiang.kotlin.base.data.protocol.BaseResp
import com.zhongjiang.kotlin.base.rx.BaseFunc
import com.zhongjiang.kotlin.base.rx.BaseFuncBoolean
import com.zhongjiang.kotlin.base.rx.BaseFuncList
import com.zhongjiang.kotlin.base.rx.BaseSubscriber
import rx.Observable
import rx.Subscription
import rx.schedulers.Schedulers

/**
 * Created by dyn on 2018/7/13.
 * 扩展
 */
fun <T> Observable<T>.execute(subscriber: BaseSubscriber<T>, lifecycleProvider: LifecycleProvider<*>
): Subscription {
    return this.observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribeOn(Schedulers.io())
            .subscribe(subscriber)
}

fun <T> Observable<BaseResp<T>>.convert(): Observable<T> {
    return this.flatMap(BaseFunc())
}
fun <T> Observable<BaseListResp<T>>.convertList(): Observable<T>? {
    return this.flatMap(BaseFuncList())
}
fun <T> Observable<BaseResp<T>>.convertBoolean(): Observable<Boolean> {
    return this.flatMap(BaseFuncBoolean())
}

fun View.OnClick(listener: View.OnClickListener) {
    this.setOnClickListener(listener)
}

fun View.OnClick(method: () -> Unit) {
    this.setOnClickListener { method() }
}

fun Button.enable(et:EditText,method: () -> Boolean){
    val button = this
    et.addTextChangedListener(object :DefaultTextWatcher(){
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            button.isEnabled = method()
        }
    })
}