package com.zhongjiang.hotel.base.keyboard

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager

/**
 * Created by yshrsmz on 15/03/17.
 */
object KeyboardVisibilityEvent {

    private const val KEYBOARD_MIN_HEIGHT_RATIO = 0.15

    /**
     * Set keyboard visibility change event stateListener.
     * This automatically remove registered event stateListener when the Activity is destroyed
     *
     * @param activity Activity
     * @param listener KeyboardVisibilityEventListener
     */
    fun setEventListener(activity: Activity,
                         listener: KeyboardVisibilityEventListener) {

        val unregistrar = registerEventListener(activity, listener)
        activity.application
                .registerActivityLifecycleCallbacks(object : AutoActivityLifecycleCallback(activity) {
                    override fun onTargetActivityDestroyed() {
                        unregistrar?.let {
                            it.unregister()
                        }
                    }
                })
    }

    /**
     * Set keyboard visibility change event stateListener.
     *
     * @param activity Activity
     * @param listener KeyboardVisibilityEventListener
     * @return Unregistrar
     */
    private fun registerEventListener(activity: Activity,
                                      listener: KeyboardVisibilityEventListener): Unregistrar? {

        if (activity == null) {
            return null
        }

        val softInputAdjust = activity.window.attributes.softInputMode and WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST

        // fix for #37 and #38.
        // The window will not be resized in case of SOFT_INPUT_ADJUST_NOTHING
        if (softInputAdjust and WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING == WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING) {
            return null
        }

        if (listener == null) {
            return null
        }

        val activityRoot = getActivityRoot(activity)

        val layoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {

            private val r = Rect()

            private var wasOpened = false

            override fun onGlobalLayout() {
                activityRoot.getWindowVisibleDisplayFrame(r)

                val screenHeight = activityRoot.rootView.height
                val heightDiff = screenHeight - r.height()

                val isOpen = heightDiff > screenHeight * KEYBOARD_MIN_HEIGHT_RATIO

                if (isOpen == wasOpened) {
                    // keyboard state has not changed
                    return
                }

                wasOpened = isOpen

                listener.onVisibilityChanged(isOpen)
            }
        }
        activityRoot.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)

        return SimpleUnregistrar(activity, layoutListener)
    }


    /**
     * Determine if keyboard is visible
     *
     * @param activity Activity
     * @return Whether keyboard is visible or not
     */
    fun isKeyboardVisible(activity: Activity): Boolean {
        val r = Rect()

        val activityRoot = getActivityRoot(activity)

        activityRoot.getWindowVisibleDisplayFrame(r)

        val screenHeight = activityRoot.rootView.height
        val heightDiff = screenHeight - r.height()

        return heightDiff > screenHeight * KEYBOARD_MIN_HEIGHT_RATIO
    }

    internal fun getActivityRoot(activity: Activity): View {
        return (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
    }
}
