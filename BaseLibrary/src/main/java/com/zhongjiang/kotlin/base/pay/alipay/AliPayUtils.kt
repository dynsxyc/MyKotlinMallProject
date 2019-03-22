package com.zhongjiang.kotlin.base.pay.alipay

import android.app.Activity
import android.text.TextUtils
import android.widget.Toast
import com.alipay.sdk.app.PayTask
import com.zhongjiang.kotlin.base.ext.handlerThread
import com.zhongjiang.kotlin.base.injection.module.sheduler.SchedulerProvider
import io.reactivex.Maybe

class AliPayUtils {
    companion object {
        fun startPay(activity:Activity,orderInfo:String,schedulers: SchedulerProvider,result:(resultCode:Boolean,showMsg:String)->Unit){

            // 调用支付接口，获取支付结果
            Maybe.just(activity).map {
                val alipay = PayTask(it)
                alipay.pay(orderInfo,true)
            }.handlerThread(schedulers).subscribe({
                val payResult = PayResult(it)

                // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                val resultInfo = payResult.result

                val resultStatus = payResult.resultStatus
                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show()
                    result(true,"支付成功")
                } else {
                    var showMsg = "抱歉，支付失败"
                    // 判断resultStatus 为非“9000”则代表可能支付失败
                    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        Toast.makeText(activity, "支付结果确认中", Toast.LENGTH_SHORT)
                                .show()
                        showMsg = "支付结果确认中"
                    } else if (TextUtils.equals(resultStatus, "4000")) {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        Toast.makeText(activity, payResult.memo, Toast.LENGTH_SHORT)
                                .show()
                        payResult.memo?.let {
                            showMsg = it
                        }
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        Toast.makeText(activity, "用户中途取消支付", Toast.LENGTH_SHORT)
                                .show()
                        showMsg = "用户中途取消支付";
                    } else {
                        Toast.makeText(activity, "抱歉，支付失败", Toast.LENGTH_SHORT)
                                .show()
                    }
                    result(false,showMsg)
                }
            },{

            })
        }
    }
}