package com.zhongjiang.youxuan.base.keyboard

import android.app.Activity
import android.view.ViewTreeObserver
import java.lang.ref.WeakReference

/**
 * @author Anoop S S
 * anoopvvs@gmail.com
 * on 28/02/2017
 */

class SimpleUnregistrar internal constructor(activity: Activity, globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener) : Unregistrar {

    private val mActivityWeakReference: WeakReference<Activity> = WeakReference(activity)

    private val mOnGlobalLayoutListenerWeakReference: WeakReference<ViewTreeObserver.OnGlobalLayoutListener> = WeakReference(globalLayoutListener)

    override fun unregister() {
        val activity = mActivityWeakReference.get()
        val globalLayoutListener = mOnGlobalLayoutListenerWeakReference.get()

        if (null != activity && null != globalLayoutListener) {
            val activityRoot = KeyboardVisibilityEvent.getActivityRoot(activity)
                activityRoot.viewTreeObserver
                        .removeOnGlobalLayoutListener(globalLayoutListener)
        }

        mActivityWeakReference.clear()
        mOnGlobalLayoutListenerWeakReference.clear()
    }

}
