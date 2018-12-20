@file:Suppress("UNREACHABLE_CODE")

package com.zhongjiang.kotlin.base.ext

import android.view.View
import android.widget.Button
import android.widget.EditText
import com.kotlin.base.widgets.DefaultTextWatcher
import com.zhongjiang.kotlin.base.data.protocol.BaseListResp
import com.zhongjiang.kotlin.base.data.protocol.BaseResp
import com.zhongjiang.kotlin.base.injection.module.sheduler.SchedulerProvider
import com.zhongjiang.kotlin.base.rx.BaseFunc
import com.zhongjiang.kotlin.base.rx.BaseFuncBoolean
import com.zhongjiang.kotlin.base.rx.BaseFuncList
import io.reactivex.Maybe

/**
 * Created by dyn on 2018/7/13.
 * 扩展
 */
fun <T> Maybe<T>.handlerThread(schedulers: SchedulerProvider): Maybe<T> {
    return this.subscribeOn(schedulers.io()).observeOn(schedulers.ui());
}
fun <T> Maybe<BaseResp<T>>.convert(): Maybe<T> {
    return this.flatMap(BaseFunc())
}
fun <T> Maybe<BaseListResp<T>>.convertList(): Maybe<T>{
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

fun Button.enable(et:EditText,method: () -> Boolean){
    val button = this
    et.addTextChangedListener(object :DefaultTextWatcher(){
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            button.isEnabled = method()
        }
    })
}