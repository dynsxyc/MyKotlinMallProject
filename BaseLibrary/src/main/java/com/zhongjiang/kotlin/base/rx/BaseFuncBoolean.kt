package com.zhongjiang.kotlin.base.rx

import com.zhongjiang.kotlin.base.common.ResultCode.Companion.SUCCESS
import com.zhongjiang.kotlin.base.data.protocol.BaseResp
import rx.Observable
import rx.functions.Func1

/**
 * Created by dyn on 2018/7/17.
 */
class  BaseFuncBoolean<T> :Func1<BaseResp<T>,Observable<Boolean>> {
    override fun call(t: BaseResp<T>): Observable<Boolean> {
        if (t.status != SUCCESS){
            return Observable.error(BaseException(t.status,t.showMessage))
        }
        return Observable.just(true)
    }

}