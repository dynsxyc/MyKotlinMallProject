package com.zhongjiang.kotlin.base.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.widget.ImageView
import com.zhongjiang.kotlin.base.R
import org.jetbrains.anko.find

/**
 * Created by dyn on 2018/7/17.
 */
class ProgressLoading private constructor(context: Context,theme:Int) :Dialog(context,theme) {

    companion object {
        private var cancelable:Boolean = false
        private lateinit var mDialog:ProgressLoading
        private var animDrawable :AnimationDrawable ?= null
        fun create(context: Context):ProgressLoading{
            mDialog = ProgressLoading(context, R.style.LightProgressDialog)
            mDialog.setContentView(R.layout.progress_dialog)
            mDialog.setCancelable(cancelable)
            mDialog.setCanceledOnTouchOutside(cancelable)
            mDialog.window.attributes.gravity = Gravity.CENTER
            val  lp = mDialog.window.attributes
                    lp.dimAmount = 0.2f
            mDialog.window.attributes = lp
                    val  loadingView = mDialog.find<ImageView>(R.id.iv_loading)
            animDrawable = loadingView.background as AnimationDrawable

            return mDialog
        }
        fun create(context: Context,cancelable:Boolean):ProgressLoading{
            this.cancelable = cancelable
            return create(context)
        }
    }
    fun showLoading(){
        super.show()
        animDrawable?.start()
    }
    fun hideLoading(){
        super.dismiss()
        animDrawable?.stop()
    }
}