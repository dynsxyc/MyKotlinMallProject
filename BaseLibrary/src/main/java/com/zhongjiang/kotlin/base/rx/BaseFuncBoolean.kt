package com.zhongjiang.kotlin.base.rx

import com.zhongjiang.kotlin.base.common.ResultCode.Companion.SUCCESS
import com.zhongjiang.kotlin.base.data.protocol.BaseResp
import io.reactivex.Maybe
import io.reactivex.functions.Function

/**
 * Created by dyn on 2018/7/17.
 */
class  BaseFuncBoolean<T> : Function<BaseResp<T>, Maybe<Boolean>> {
    override fun apply(t: BaseResp<T>): Maybe<Boolean> {

        if (t.status != SUCCESS){
            return Maybe.error(BaseException(t.status,t.showMessage))
        }
        return Maybe.just(true)
    }

}