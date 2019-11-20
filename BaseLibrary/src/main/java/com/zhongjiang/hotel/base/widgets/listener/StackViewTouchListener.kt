package com.zhongjiang.hotel.base.widgets.listener

import android.view.MotionEvent
import android.view.View

class StackViewTouchListener constructor(private val stackView: View, private val clickLimitValue: Int) : View.OnTouchListener {
    private var dX: Float = 0.toFloat()
    private var dY = 0f
    private var downX: Float = 0.toFloat()
    private var downY = 0f
    private var isClickState: Boolean = false

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val X = event.rawX
        val Y = event.rawY
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isClickState = true
                downX = X
                downY = Y
                dX = stackView.x - event.rawX
                dY = stackView.y - event.rawY
            }
            MotionEvent.ACTION_MOVE -> if (Math.abs(X - downX) < clickLimitValue && Math.abs(Y - downY) < clickLimitValue && isClickState) {
                isClickState = true
            } else {
                isClickState = false
                stackView.x = event.rawX + dX
                stackView.y = event.rawY + dY
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> if (X - downX < clickLimitValue && isClickState) {
                stackView.performClick()
            }
            else -> return false
        }
        return true
    }
}