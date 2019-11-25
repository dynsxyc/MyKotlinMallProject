package com.zhongjiang.hotel.base.rx

import com.zhongjiang.hotel.base.common.ResultCode.Companion.SUCCESS
import com.zhongjiang.hotel.base.data.protocol.BaseResp
import io.reactivex.Maybe
import io.reactivex.MaybeSource
import io.reactivex.functions.Function

/**
 * Created by dyn on 2018/7/17.
 */
class BaseFunc<T> : Function<BaseResp<T>, MaybeSource<T>> {
    override fun apply(t: BaseResp<T>): MaybeSource<T> {
        if (t.status != SUCCESS){
            return Maybe.error(ResponeThrowable(null, ExceptionHandle.ERROR.PROFESSIONAL_ERROR,t.status,t.showMessage))
        }
        return Maybe.just(t.data)
    }
}