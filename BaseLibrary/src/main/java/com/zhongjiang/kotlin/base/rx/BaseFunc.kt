package com.zhongjiang.kotlin.base.rx

import com.zhongjiang.kotlin.base.common.ResultCode.Companion.SUCCESS
import com.zhongjiang.kotlin.base.data.protocol.BaseResp
import rx.Observable
import rx.functions.Func1

/**
 * Created by dyn on 2018/7/17.
 */
class BaseFunc<T> : Func1<BaseResp<T>, Observable<T>> {
    override fun call(t: BaseResp<T>): Observable<T> {
        if (t.status != SUCCESS){
            return Observable.error(BaseException(t.status, t.showMessage))
        }
        return Observable.just(t.data)
    }

}