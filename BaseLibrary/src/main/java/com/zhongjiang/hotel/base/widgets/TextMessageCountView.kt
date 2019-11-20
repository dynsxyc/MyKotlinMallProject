package com.zhongjiang.hotel.base.widgets

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.zhongjiang.youxuan.base.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_tv_merge_message_count.*
import org.jetbrains.anko.sp


/**
 * @date on 2019/04/25 16:20
 * @packagename tsou.uxuan.user.mView
 * @author dyn
 * @org com.zhongjiang.youxuan
 * @describe 自定义  消息红点样式
 * @email 583454199@qq.com
 */

class TextMessageCountView constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    /**
     * 最大消息数量，大于这个 显示...
     */
    private var mMaxMessageCount = DEFAULT_MAX_COUNT
    private var mViewHolder: ViewHolder

    /**显示数字 不显示红点状态
     * 数字为0 这个View InVisible
     */
    private var mMessageCount: Int = 0
    fun getMessageCount() = mMessageCount

    fun setMessageCount(mMessageCount:Int) {
            this.mMessageCount = mMessageCount
        mViewHolder.let {
            it.setViewMessageCount(mMessageCount)
        }
        }
    fun setMaxMessageCount(maxCount:Int){
        this.mMaxMessageCount = maxCount
        mViewHolder.let {
            it.setMaxMessageCount(maxCount)
        }
    }

    init {
        var  text:String? = ""
        var  textColor = ContextCompat.getColor(getContext(),R.color.text_color_33)
        var  textSize = 16f
        var  drawableLeft:Drawable? = null
        var  drawableRight:Drawable? = null
        var  imgDrawable:Drawable? = null
        var  drawablePadding = 0
        var isImgLable = false
        var backgroundDrawable:Drawable?= ColorDrawable(Color.parseColor("#ffffff"))
        View.inflate(context, R.layout.view_tv_merge_message_count, this)
        mViewHolder = ViewHolder(this)

        val a = context.obtainStyledAttributes(attrs, R.styleable.TextMessageCountView, defStyleAttr, 0)
        try {
            mMessageCount = a.getInt(R.styleable.TextMessageCountView_tv_messageCount, 0)
            mMaxMessageCount = a.getInt(R.styleable.TextMessageCountView_tv_maxMessageCount, DEFAULT_MAX_COUNT)
            text = a.getString(R.styleable.TextMessageCountView_tv_text)
            textColor = a.getColor(R.styleable.TextMessageCountView_tv_textColor,ContextCompat.getColor(getContext(),R.color.text_color_33))
            textSize = a.getDimension(R.styleable.TextMessageCountView_tv_textSize, sp(16).toFloat())
            drawableLeft = a.getDrawable(R.styleable.TextMessageCountView_tv_drawableLeft)
            drawableRight = a.getDrawable(R.styleable.TextMessageCountView_tv_drawableRight)
            imgDrawable = a.getDrawable(R.styleable.TextMessageCountView_tv_imgDrawable)
            backgroundDrawable = a.getDrawable(R.styleable.TextMessageCountView_tv_backgroundDrawable)
            drawablePadding = a.getDimensionPixelSize(R.styleable.TextMessageCountView_tv_drawablePadding,0)
            isImgLable = a.getBoolean(R.styleable.TextMessageCountView_tv_isImgLabel,false)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            a.recycle()
        }
        setText(text = text)
        setTextColor(textColor)
        setTextSize(textSize)
        setCompoundDrawableLeft(drawableLeft,null,drawableRight,null)
        setCompoundDrawablePadding(drawablePadding)
        setMessageCount(mMessageCount)
        setMaxMessageCount(mMaxMessageCount)
        setIsImgLabel(isImgLable)
        setImgDrawable(imgDrawable)
        background = backgroundDrawable
    }


    /**
     * 隐藏数字 只显示红点状态
     */
    fun showMessageStatus() {
        mViewHolder.let {
            it.showMessageStatus()
        }
    }

    fun setTextColor(@ColorInt textColor:Int){
        mViewHolder.setTextColor(textColor)
    }

    fun setTextSize(textSize: Float){
        mViewHolder.setTextSize(textSize)
    }

    fun setText(text:String?){
        mViewHolder.setText(text)
    }
    fun setCompoundDrawableLeft( drawableLeft:Drawable?,drawableTop: Drawable?,drawableRight: Drawable?,drawableBottom: Drawable?){
        mViewHolder.setCompoundDrawable(drawableLeft,drawableTop,drawableRight,drawableBottom)
    }
    fun setCompoundDrawablePadding(drawablePad:Int){
        mViewHolder.setCompoundDrawablePadding(drawablePad)
    }
    fun setIsImgLabel(isImgLabel: Boolean){
        mViewHolder.setIsImgLabel(isImgLabel)
    }
    fun setImgDrawable(imgDrawable: Drawable?){
        mViewHolder.setImgDrawable(imgDrawable)
    }

    fun setTheme(typeArray: TypedArray){
        mViewHolder.setTheme(typeArray)
    }

    companion object {
        private const val DEFAULT_MAX_COUNT = 99
    }

    private class ViewHolder(override val containerView: View) : LayoutContainer {
        fun setViewMessageCount(mMessageCount: Int) {
            mTextMessageCountViewMessageCountView?.let {
                it.setMessageCount(mMessageCount)
            }
        }
        fun showMessageStatus() {
            mTextMessageCountViewMessageCountView?.let {
                mTextMessageCountViewMessageCountView.showMessageStatus()
            }
        }
        fun setMaxMessageCount(maxMessageCount:Int){
            mTextMessageCountViewMessageCountView?.let {
                mTextMessageCountViewMessageCountView.setMaxMessageCount(maxMessageCount)
            }
        }
        fun setTextColor(@ColorInt textColor: Int){
            mTextMessageCountViewTv?.let {
                it.setTextColor(textColor)
            }
        }
        fun setText(text:String?){
            mTextMessageCountViewTv.let {
                it.text = text
            }
        }
        fun setTextSize(textSize:Float){
            mTextMessageCountViewTv.let {
                it.setTextSize(COMPLEX_UNIT_PX, textSize)
            }
        }
        fun setCompoundDrawable(drawableLeft:Drawable?,drawableTop:Drawable?,drawableRight:Drawable?,drawableBottom:Drawable?){
            mTextMessageCountViewTv.let {
                it.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,drawableTop,drawableRight,drawableBottom)
            }
        }
        fun setCompoundDrawablePadding(drawablePad:Int){
            mTextMessageCountViewTv.let {
                it.compoundDrawablePadding = drawablePad
            }
        }
        fun setIsImgLabel(isImgLabel:Boolean){
           mTextMessageCountViewImgIcon.visibility = if (isImgLabel) View.VISIBLE else View.GONE
           mTextMessageCountViewTv.visibility = if (isImgLabel) View.GONE else View.VISIBLE
        }
        fun setImgDrawable(imgDrawable:Drawable?){
           mTextMessageCountViewImgIcon.setImageDrawable(imgDrawable)
        }

        fun setTheme(typeArray: TypedArray){
            try {
                var mMessageCount = typeArray.getInt(R.styleable.TextMessageCountView_tv_messageCount, 0)
                var mMaxMessageCount = typeArray.getInt(R.styleable.TextMessageCountView_tv_maxMessageCount, DEFAULT_MAX_COUNT)
                var text = typeArray.getString(R.styleable.TextMessageCountView_tv_text)
                var textColor = typeArray.getColor(R.styleable.TextMessageCountView_tv_textColor, ContextCompat.getColor(containerView.context, R.color.text_color_33))
                var textSize = typeArray.getDimension(R.styleable.TextMessageCountView_tv_textSize, containerView.sp(16).toFloat())
                var drawableLeft = typeArray.getDrawable(R.styleable.TextMessageCountView_tv_drawableLeft)
                var drawableRight = typeArray.getDrawable(R.styleable.TextMessageCountView_tv_drawableRight)
                var imgDrawable = typeArray.getDrawable(R.styleable.TextMessageCountView_tv_imgDrawable)
                var drawablePadding = typeArray.getDimensionPixelSize(R.styleable.TextMessageCountView_tv_drawablePadding, 0)
                var isImgLable = typeArray.getBoolean(R.styleable.TextMessageCountView_tv_isImgLabel, false)
                var backgroundDrawable = typeArray.getDrawable(R.styleable.TextMessageCountView_tv_backgroundDrawable)
                setViewMessageCount(mMessageCount)
                setMaxMessageCount(mMaxMessageCount)
                setText(text)
                setTextColor(textColor)
                setTextSize(textSize)
                setCompoundDrawable(drawableLeft, null, drawableRight, null)
                setImgDrawable(imgDrawable)
                setCompoundDrawablePadding(drawablePadding)
                setIsImgLabel(isImgLable)
                containerView.background = backgroundDrawable
            }catch ( e:java.lang.Exception){
                e.printStackTrace()
            }finally {
                typeArray.recycle()
            }
        }

    }
}
