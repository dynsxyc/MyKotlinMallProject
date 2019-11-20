package com.zhongjiang.hotel.base.ui.fragment.dialog

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.gyf.barlibrary.ImmersionBar
import com.zhongjiang.youxuan.base.R

/**
 * DialogFragment 实现沉浸式的基类
 *
 * @author geyifeng
 * @date 2017 /8/26
 */
abstract class BaseDialogFragment : DialogFragment() {
    protected lateinit var mRootView: View
    protected var mWindow: Window? = null
    /**
     * 屏幕宽度
     */
    protected var mWidth: Int = 0
    /**
     * 屏幕高度
     */
    protected var mHeight: Int = 0

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected val isImmersionBarEnabled: Boolean
        get() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //全屏
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        //点击外部消失
        dialog?.setCanceledOnTouchOutside(true)
        mWindow = dialog?.window
        //测量宽高
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val dm = DisplayMetrics()
            activity?.getWindowManager()?.defaultDisplay?.getRealMetrics(dm)
            mWidth = dm.widthPixels
            mHeight = dm.heightPixels
        } else {
            val metrics = resources.displayMetrics
            mWidth = metrics.widthPixels
            mHeight = metrics.heightPixels
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(setLayoutId(), container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isImmersionBarEnabled) {
            initImmersionBar()
        }
        initData()
        initView()
        setListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy()
    }

    /**
     * Sets layout id.
     *
     * @return the layout id
     */
    protected abstract fun setLayoutId(): Int

    /**
     * 初始化沉浸式
     */
    protected open fun initImmersionBar() {
        ImmersionBar.with(this).init()
    }


    /**
     * 初始化数据
     */
    protected fun initData() {

    }

    /**
     * view与数据绑定
     */
    protected fun initView() {

    }

    /**
     * 设置监听
     */
    protected fun setListener() {

    }
}
