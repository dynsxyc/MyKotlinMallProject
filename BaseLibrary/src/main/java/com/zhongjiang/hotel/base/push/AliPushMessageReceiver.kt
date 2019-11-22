package com.zhongjiang.hotel.base.push

import android.content.Context
import com.alibaba.sdk.android.push.MessageReceiver
import com.alibaba.sdk.android.push.notification.CPushMessage
import com.zhongjiang.hotel.base.utils.ULogger

class AliPushMessageReceiver : MessageReceiver() {
    companion object {
        val REC_TAG = "alipushReceiver"
    }

    override fun onNotification(context:Context, title : String, summary:String, extraMap : Map<String, String>) {
        super.onNotification(context,title,summary,extraMap)
        //处理推送通知
        ULogger.i("onNotification   Receive notification, title:   $title , summary:   $summary , extraMap: $extraMap")
    }

    override fun onMessage(context: Context, cPushMessage: CPushMessage) {
        super.onMessage(context,cPushMessage)
        ULogger.i("onMessage messageId:${cPushMessage.messageId} ${cPushMessage.content}   ")
    }

    public override fun onNotificationOpened(context: Context?, title: String?, summary: String?, extraMap: String?) {
        super.onNotificationOpened(context,title,summary,extraMap)
        ULogger.i("onNotificationOpened, title: $title, summary: $summary, extraMap:$extraMap")
    }

    override fun onNotificationClickedWithNoAction(context: Context?, title: String?, summary: String?, extraMap: String?) {
        super.onNotificationClickedWithNoAction(context,title,summary,extraMap)
        ULogger.i("onNotificationClickedWithNoAction, title: $title, summary: $summary, extraMap:$extraMap")
    }

    override fun onNotificationReceivedInApp(context: Context?, title: String?, summary: String?, extraMap: Map<String, String>?, openType: Int, openActivity: String?, openUrl: String?) {
        super.onNotificationReceivedInApp(context,title,summary,extraMap,openType,openActivity,openUrl)
        ULogger.i("onNotificationReceivedInApp, title: $title, summary: $summary, extraMap:$extraMap, openType:$openType, openActivity:$openActivity, openUrl:$openUrl")
    }

    override fun onNotificationRemoved(context: Context?, messageId: String?) {
        super.onNotificationRemoved(context,messageId)
        ULogger.i( "onNotificationRemoved")
    }
}