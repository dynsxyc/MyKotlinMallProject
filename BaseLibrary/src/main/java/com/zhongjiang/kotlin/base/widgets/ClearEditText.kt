package com.zhongjiang.kotlin.base.widgets

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import com.zhongjiang.kotlin.base.R
import org.jetbrains.anko.dip

class ClearEditText constructor(context:Context): AppCompatEditText(context) {
    /** 默认的清除按钮图标资源  */
    private val ICON_CLEAR_DEFAULT = R.drawable.img_login_delete

    lateinit var drawableClear:Drawable

    constructor(context: Context,attributes: AttributeSet) : this(context){
        init(context,attributes)
    }

    fun init(context: Context ,attributes: AttributeSet) {
        // 获取自定义属性
        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.ClearEditText)
        // 获取清除按钮图标资源
        val iconClear = typedArray.getResourceId(R.styleable.ClearEditText_iconClear, ICON_CLEAR_DEFAULT)
        drawableClear = resources.getDrawable(iconClear)
        updateIconClear()
        typedArray.recycle()
        compoundDrawablePadding = dip(5)
        // 设置TextWatcher用于更新清除按钮显示状态
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                updateIconClear()
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action === MotionEvent.ACTION_DOWN){
            // 点击是的 x 坐标
            val xDown = event.getX().toInt()
            // 清除按钮的起始区间大致为[getWidth() - getCompoundPaddingRight(), getWidth()]，
            // 点击的x坐标在此区间内则可判断为点击了清除按钮
            if (xDown >= width - compoundPaddingRight && xDown < width) {
                // 清空文本
                setText("")
            }
            updateIconClear()
        }
        return super.onTouchEvent(event)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        updateIconClear()
    }

    fun updateIconClear(){
        var drawables = compoundDrawables
        if (length()>0 && hasFocus()){
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawableClear,
                    drawables[3])
        }else{
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], null,
                    drawables[3])
        }
    }

    fun setIconClear(@DrawableRes resId:Int){
        drawableClear = resources.getDrawable(resId)
        updateIconClear()
    }
}