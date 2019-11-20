package com.zhongjiang.hotel.base.pay.weixinpay

import android.content.Context
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhongjiang.youxuan.base.utils.ULogger

open class WXPayUtils {
    companion object {
        fun startPay(context: Context, appid: String, partnerId: String, prepayId: String, packageValue: String, nonceStr: String, timeStamp: String, key: String):Int {
            val req = PayReq()
            val msgApi = WXAPIFactory.createWXAPI(context, null)
            msgApi.registerApp(appid)

            req.appId = appid
            req.partnerId = partnerId
            req.prepayId = prepayId
            req.packageValue = packageValue
            req.nonceStr = nonceStr
            req.timeStamp = timeStamp

            var signParams = mapOf(Pair("appid", appid), Pair("noncestr", nonceStr), Pair("package", packageValue), Pair("partnerid", partnerId), Pair("prepayid", prepayId), Pair("timestamp", timeStamp))
            var sign = genAppSign(signParams, key)
            req.sign = sign
            if (sign.isNullOrEmpty()){
                return 0
            }else
            if (!msgApi.isWXAppInstalled()) {
                return -1
            } else {
                msgApi.sendReq(req)
                return 1
            }
        }

        private fun genAppSign(params: Map<String,String>, key: String): String? {

            val sb = StringBuilder()

            params.map {
                sb.append("${it.key}=${it.value}&")
            }
            sb.append("key=")
            sb.append(key)

            val appSign = WXPayMD5.getMessageDigest(sb.toString().toByteArray())?.toUpperCase()
            ULogger.e("orion", appSign)
            return appSign
        }
    }
}