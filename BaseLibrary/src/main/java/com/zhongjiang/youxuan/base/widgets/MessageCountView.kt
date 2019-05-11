package com.zhongjiang.youxuan.base.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.zhongjiang.youxuan.base.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_merge_message_count.*


/**
 * @date on 2019/04/25 16:20
 * @packagename tsou.uxuan.user.view
 * @author dyn
 * @org com.zhongjiang.youxuan
 * @describe 自定义  消息红点样式
 * @email 583454199@qq.com
 */

class MessageCountView constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

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
    fun setMessageCount(mMessageCount: Int) {
        this.mMessageCount = mMessageCount
        if (mMessageCount <= 0) {
            visibility = View.INVISIBLE
            return
        }
        visibility = View.VISIBLE
        mViewHolder.let {
            it.setViewMessageCount(mMessageCount, mMaxMessageCount)
        }
    }

    /**
     * 可现实的最大值
     * @param maxCount 最大显示值
     * */
    fun setMaxMessageCount(maxCount: Int) {
        this.mMaxMessageCount = maxCount
        setMessageCount(mMessageCount)
    }


    init {
        val view = View.inflate(context, R.layout.view_merge_message_count, this)
        mViewHolder = ViewHolder(view)

        val a = context.obtainStyledAttributes(attrs, R.styleable.MessageCountView, defStyleAttr, 0)
        try {
            mMessageCount = a.getInt(R.styleable.MessageCountView_messageCount, 0)
            mMaxMessageCount = a.getInt(R.styleable.MessageCountView_maxMessageCount, DEFAULT_MAX_COUNT)
        } catch (e: Exception) {

        } finally {
            a.recycle()
        }
        setMessageCount(mMessageCount)
    }


    /**
     * 隐藏数字 只显示红点状态
     */
    fun showMessageStatus() {
        visibility = View.VISIBLE
        mViewHolder.let {
            it.showMessageStatus()
        }
    }

    companion object {
        private const val DEFAULT_MAX_COUNT = 99
    }

    private class ViewHolder(override val containerView: View?) : LayoutContainer {
        fun setViewMessageCount(mMessageCount: Int, mMaxMessageCount: Int) {
            mMessageCountViewRoundTvStatus?.let {
                it.visibility = View.GONE
            }
            mMessageCountViewRoundTvCount?.let {
                it.visibility = View.VISIBLE
                if (mMessageCount > mMaxMessageCount) {
                    it.text = "$mMaxMessageCount+"
                } else {
                    it.text = mMessageCount.toString()
                }
            }
        }

        fun showMessageStatus() {
            mMessageCountViewRoundTvCount?.let {
                it.visibility = View.GONE
            }
            mMessageCountViewRoundTvStatus?.let {
                it.visibility = View.VISIBLE
            }
        }
    }
}
