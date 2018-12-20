package com.zhongjiang.kotlin.base.rx

import com.zhongjiang.kotlin.base.common.ResultCode.Companion.SUCCESS
import com.zhongjiang.kotlin.base.data.protocol.BaseListResp
import io.reactivex.Maybe
import io.reactivex.functions.Function

/**
 * Created by dyn on 2018/7/17.
 */
class BaseFuncList<T> : Function<BaseListResp<T>, Maybe<T>> {
    override fun apply(t: BaseListResp<T>): Maybe<T> {
        if (t.status != SUCCESS){
            return Maybe.error(BaseException(t.status, t.showMessage))
        }
        return Maybe.just(t.data.dataList)
    }

}