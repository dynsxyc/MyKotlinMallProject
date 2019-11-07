package com.zhongjiang.youxuan.base.widgets

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.jakewharton.rxbinding2.view.RxView
import com.zhongjiang.youxuan.base.R
import com.zhongjiang.youxuan.base.ext.shieldDoubleClick
import com.zhongjiang.youxuan.base.widgets.listener.OnCommonHeaderTitleViewClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_header_title.*


/**
 * @date on 2019/5/8 17:51
 * @packagename com.zhongjiang.youxuan.base.widgets
 * @author dyn
 * @fileName CommonHeaderTitleView
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
class CommonHeaderTitleView constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : RelativeLayout(context, attrs, defStyleAttr) {
    private var mViewHolder:HeaderTitleViewHolder
    constructor(context: Context):this(context,null)
    constructor(context: Context,attrs: AttributeSet?):this(context,attrs,0)
    init {
        var view = inflate(context, R.layout.view_header_title,this)
        this.mViewHolder = HeaderTitleViewHolder(view)
        val a = context.obtainStyledAttributes(attrs, R.styleable.CommonHeaderTitleView, defStyleAttr, R.style.HeaderTitleViewDefault)
        var titleStr :String? = ""
        var hasFinish = false
        var hasRight = false
        var hasLastRight = false
        var themeBack = 0
        var themeFinish=0
        var themeRight=0
        var themeLastRight=0
        try {
            titleStr = a.getString(R.styleable.CommonHeaderTitleView_cht_TitleText)
            hasFinish = a.getBoolean(R.styleable.CommonHeaderTitleView_cht_hasFinish,false)
            hasRight = a.getBoolean(R.styleable.CommonHeaderTitleView_cht_hasRight,false)
            hasLastRight = a.getBoolean(R.styleable.CommonHeaderTitleView_cht_hasLastRight,false)
            themeBack = a.getResourceId(R.styleable.CommonHeaderTitleView_cht_themeBack,R.style.HeaderTitleImgDefault_Back)
            themeFinish = a.getResourceId(R.styleable.CommonHeaderTitleView_cht_themeFinish,R.style.HeaderTitleImgDefault_Back)
            themeRight = a.getResourceId(R.styleable.CommonHeaderTitleView_cht_themeRight,R.style.HeaderTitleImgDefault_Back)
            themeLastRight = a.getResourceId(R.styleable.CommonHeaderTitleView_cht_themeLastRight,R.style.HeaderTitleImgDefault_Back)
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            a.recycle()
        }
        setTitleContent(titleStr)
        setHasFinish(hasFinish)
        setHasRight(hasRight)
        setHasLastRight(hasLastRight)
        setBackgroundColor(resources.getColor(R.color.white))

        setTextMessageCountViewTheme(themeBack,themeFinish,themeRight,themeLastRight)

    }

    private class HeaderTitleViewHolder(override val containerView: View?) : LayoutContainer{
        fun setTitle(title: String?){
            mCommonHeaderTitleViewTvCenter?.let {
                it.text = title
            }
        }
        fun setHasFinish(boolean: Boolean){
            mCommonHeaderTitleViewTvFinish?.let {
                it.visibility = if (boolean) View.VISIBLE else View.GONE
            }
        }
        fun setHasRight(boolean: Boolean){
            mCommonHeaderTitleViewTvRight?.let {
                it.visibility = if (boolean) View.VISIBLE else View.GONE
            }
        }
        fun setHasLastRight(boolean: Boolean){
            mCommonHeaderTitleViewTvLastRight?.let {
                it.visibility = if (boolean) View.VISIBLE else View.GONE
            }
        }

        fun setBackTheme(typeArray: TypedArray){
            mCommonHeaderTitleViewTvBack?.let {
                it.setTheme(typeArray)
            }
        }
        fun setFinishTheme(typeArray: TypedArray){
            mCommonHeaderTitleViewTvFinish?.let {
                it.setTheme(typeArray)
            }
        }
        fun setRightTheme(typeArray: TypedArray){
            mCommonHeaderTitleViewTvRight?.let {
                it.setTheme(typeArray)
            }
        }
        fun setLastRightTheme(typeArray: TypedArray){
            mCommonHeaderTitleViewTvLastRight?.let {
                it.setTheme(typeArray)
            }
        }
        fun setOnViewClickListener(onCommonHeaderTitleViewClickListener: OnCommonHeaderTitleViewClickListener?){
            RxView.clicks(mCommonHeaderTitleViewTvBack).shieldDoubleClick {
                onCommonHeaderTitleViewClickListener?.let {
                    it.onBack(mCommonHeaderTitleViewTvBack)
                }
            }
            RxView.clicks(mCommonHeaderTitleViewTvFinish).shieldDoubleClick {
                onCommonHeaderTitleViewClickListener?.let {
                    it.onFinish(mCommonHeaderTitleViewTvFinish)
                }
            }
            RxView.clicks(mCommonHeaderTitleViewTvRight).shieldDoubleClick {
                onCommonHeaderTitleViewClickListener?.let {
                    it.onRight(mCommonHeaderTitleViewTvRight)
                }
            }
            RxView.clicks(mCommonHeaderTitleViewTvLastRight).shieldDoubleClick {
                onCommonHeaderTitleViewClickListener?.let {
                    it.onLastRight(mCommonHeaderTitleViewTvLastRight)
                }
            }
        }
    }
    /**
     * 设置标题内容
     * */
    fun setTitleContent(content:String?){
        mViewHolder.let {
            it.setTitle(content)
        }
    }
    /**
     * 是否显示左边两个按钮的finish按钮
     * */
    fun setHasFinish(boolean: Boolean){
        mViewHolder.let {
            it.setHasFinish(boolean)
        }
    }
    /**
     * 是否显示右边两个按钮的左按钮
     * */
    fun setHasRight(boolean: Boolean){
        mViewHolder?.let {
            it.setHasRight(boolean)
        }
    }
    /**
     * 是否显示右边两个按钮的右按钮
     * */
    fun setHasLastRight(boolean: Boolean){
        mViewHolder.let {
            it.setHasLastRight(boolean)
        }
    }
    /**
     * 设置按钮事件响应
     * */
    fun setOnViewClickListener(onCommonHeaderTitleViewClickListener: OnCommonHeaderTitleViewClickListener){
        mViewHolder.setOnViewClickListener(onCommonHeaderTitleViewClickListener)
    }
    /**
     * 设置按钮样式style
     * */
    fun setTextMessageCountViewTheme(themeBack:Int,themeFinish:Int,themeRight:Int,themeLastRight:Int){
        mViewHolder.let {
            var themeBackTypedArray =  context.obtainStyledAttributes(null,R.styleable.TextMessageCountView,0,themeBack)
            var themeFinishTypedArray =  context.obtainStyledAttributes(null,R.styleable.TextMessageCountView,0,themeFinish)
            var themeRightTypedArray =  context.obtainStyledAttributes(null,R.styleable.TextMessageCountView,0,themeRight)
            var themeLastRightTypedArray =  context.obtainStyledAttributes(null,R.styleable.TextMessageCountView,0,themeLastRight)
            it.setBackTheme(themeBackTypedArray)
            it.setFinishTheme(themeFinishTypedArray)
            it.setRightTheme(themeRightTypedArray)
            it.setLastRightTheme(themeLastRightTypedArray)
        }
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val widthMode = MeasureSpec.getMode(widthMeasureSpec)   //获取宽的模式
//        val heightMode = MeasureSpec.getMode(heightMeasureSpec) //获取高的模式
//        val widthSize = MeasureSpec.getSize(widthMeasureSpec)   //获取宽的尺寸
//        val heightSize = MeasureSpec.getSize(heightMeasureSpec) //获取高的尺寸
//        Log.v("test", "宽的模式:$widthMode")
//        Log.v("test", "高的模式:$heightMode")
//        Log.v("test", "宽的尺寸:$widthSize")
//        Log.v("test", "高的尺寸:$heightSize")
//        val width: Int
//        val height: Int
//        if (widthMode == MeasureSpec.EXACTLY) {
//            //如果match_parent或者具体的值，直接赋值
//            width = widthSize
//        } else {
//            //如果是wrap_content，我们要得到控件需要多大的尺寸
//            val textWidth = mBound.width()   //文本的宽度
//            //控件的宽度就是文本的宽度加上两边的内边距。内边距就是padding值，在构造方法执行完就被赋值
//            width = (paddingLeft.toFloat() + textWidth + paddingRight.toFloat()).toInt()
//            Log.v("test", "文本的宽度:" + textWidth + "控件的宽度：" + width)
//        }
//        //高度跟宽度处理方式一样
//        if (heightMode == MeasureSpec.EXACTLY) {
//            height = heightSize
//        } else {
//            val textHeight = mBound.height()
//            height = (paddingTop.toFloat() + textHeight + paddingBottom.toFloat()).toInt()
//            Log.v("test", "文本的高度:" + textHeight + "控件的高度：" + height)
//        }
//        //保存测量宽度和测量高度
//        setMeasuredDimension(widthSize, dip(50))
//    }
}