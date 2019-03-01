package com.zhongjiang.kotlin.base.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.flyco.roundview.RoundTextView
import com.orhanobut.logger.Logger
import com.zhongjiang.kotlin.base.BuildConfig
import com.zhongjiang.kotlin.base.R
import com.zhongjiang.kotlin.base.busevent.ActivityResultEvent
import com.zhongjiang.kotlin.base.utils.RxBus
import com.zhongjiang.kotlin.base.utils.StatusBarUtil
import com.zhongjiang.kotlin.base.widgets.listener.StackViewTouchListener
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import org.jetbrains.anko.dip
import javax.inject.Inject
import javax.inject.Singleton

abstract class BaseInjectActivity : BaseSupportActivity(),  HasSupportFragmentInjector {
    val TAG = this.javaClass.name
    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @Singleton
    lateinit var mRxBus: RxBus

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    private fun inject() {
        AndroidInjection.inject(this)
        if (injectRouter()) {
            ARouter.getInstance().inject(this)
        }
    }

    override fun onResume() {
        super.onResume()
        initStatusBar()
    }
    protected open fun initStatusBar() {
        StatusBarUtil.darkMode(this)
    }
    open fun injectRouter(): Boolean {
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Logger.i("registerActivityResultEvent isObserver ${mRxBus.isObserver()}")
        mRxBus.post(ActivityResultEvent(requestCode, resultCode, data))
        super.onActivityResult(requestCode, resultCode, data)
    }
    protected var mMainToolbarRl: LinearLayout? = null
    protected var mMainToolbarRvTvBack: RoundTextView? = null
    protected var mMainToolbarViewLine: View? = null
    protected var mMainToolbarRvTvFinish: RoundTextView? = null
    protected var mMainToolbarTvTitle: TextView? = null
    protected var mMainToolbarRvTvLastRight: RoundTextView? = null
    protected var mMainToolbarRvTvRight: RoundTextView? = null
    open fun getTopView(){
        mMainToolbarRl= findViewById(R.id.mMainToolbarRl)
        mMainToolbarRvTvBack= findViewById(R.id.mMainToolbarRvTvBack)
        mMainToolbarViewLine= findViewById(R.id.mMainToolbarViewLine)
        mMainToolbarRvTvFinish= findViewById(R.id.mMainToolbarRvTvFinish)
        mMainToolbarTvTitle= findViewById(R.id.mMainToolbarTvTitle)
        mMainToolbarRvTvLastRight= findViewById(R.id.mMainToolbarRvTvLastRight)
        mMainToolbarRvTvRight= findViewById(R.id.mMainToolbarRvTvRight)
        mMainToolbarRl?.let {
            StatusBarUtil.setPaddingSmart(this,it)
        }
    }

    fun addDebugView() {
        if (BuildConfig.DEBUG) {
            val root = findViewById<View>(android.R.id.content)
            if (root is FrameLayout) {
                val content = root as FrameLayout
                val stackView = ImageView(this)
                stackView.setImageResource(R.drawable.img_bug)
                val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                params.gravity = Gravity.BOTTOM
                val dp18 = dip(18)
                params.bottomMargin = dp18 * 7
                params.leftMargin = dp18
                params.width = dip(30)
                params.height = dip(30)
                stackView.layoutParams = params
                content.addView(stackView)
                stackView.setOnTouchListener(StackViewTouchListener(stackView, dp18 / 4))
                stackView.setOnClickListener {
                    //环境配置
                }
            }
        }
    }
}