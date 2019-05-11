package com.zhongjiang.youxuan.base.ui.activity

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
import com.gyf.barlibrary.ImmersionBar
import com.gyf.barlibrary.OSUtils
import com.orhanobut.logger.Logger
import com.zhongjiang.youxuan.base.BuildConfig
import com.zhongjiang.youxuan.base.R
import com.zhongjiang.youxuan.base.busevent.ActivityResultEvent
import com.zhongjiang.youxuan.base.utils.RxBus
import com.zhongjiang.youxuan.base.widgets.listener.StackViewTouchListener
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import org.jetbrains.anko.dip
import javax.inject.Inject
import javax.inject.Singleton

abstract class BaseInjectActivity : BaseSupportActivity(), HasSupportFragmentInjector {
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
        if (isImmersionBarEnabled()) {
            initImmersionBar()
        }
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
        // 非必加
        // 如果你的app可以横竖屏切换，适配了华为emui3系列系统手机，并且navigationBarWithEMUI3Enable为true，
        // 请在onResume方法里添加这句代码（同时满足这三个条件才需要加上代码哦：1、横竖屏可以切换；2、华为emui3系列系统手机；3、navigationBarWithEMUI3Enable为true）
        // 否则请忽略
        if (OSUtils.isEMUI3_x() && isImmersionBarEnabled()) {
            ImmersionBar.with(this).init()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isImmersionBarEnabled()) {
            ImmersionBar.with(this).destroy()
        }
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
    open fun getTopView() {
        mMainToolbarRl = findViewById(R.id.mMainToolbarRl)
        mMainToolbarRvTvBack = findViewById(R.id.mMainToolbarRvTvBack)
        mMainToolbarViewLine = findViewById(R.id.mMainToolbarViewLine)
        mMainToolbarRvTvFinish = findViewById(R.id.mMainToolbarRvTvFinish)
        mMainToolbarTvTitle = findViewById(R.id.mMainToolbarTvTitle)
        mMainToolbarRvTvLastRight = findViewById(R.id.mMainToolbarRvTvLastRight)
        mMainToolbarRvTvRight = findViewById(R.id.mMainToolbarRvTvRight)
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected open fun isImmersionBarEnabled(): Boolean {
        return true
    }

    protected open fun initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar.with(this)
//                .transparentStatusBar()  //透明状态栏，不写默认透明色
//                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
//                .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
                .statusBarColor(R.color.common_white)     //状态栏颜色，不写默认透明色
//                .navigationBarColor(R.color.colorPrimary) //导航栏颜色，不写默认黑色
//                .barColor(R.color.colorPrimary)  //同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
                .statusBarAlpha(0.3f)  //状态栏透明度，不写默认0.0f
//                .navigationBarAlpha(0.4f)  //导航栏透明度，不写默认0.0F
//                .barAlpha(0.3f)  //状态栏和导航栏透明度，不写默认0.0f
//                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
//                .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
//                .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
//                .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
//                .flymeOSStatusBarFontColor(R.color.btn3)  //修改flyme OS状态栏字体颜色
//                .fullScreen(true)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
//                .hideBar(BarHide.FLAG_HIDE_BAR)  //隐藏状态栏或导航栏或两者，不写默认不隐藏
//                .addViewSupportTransformColor(toolbar)  //设置支持view变色，可以添加多个view，不指定颜色，默认和状态栏同色，还有两个重载方法
//                .titleBar(view)    //解决状态栏和布局重叠问题，任选其一
//                .titleBarMarginTop(view)     //解决状态栏和布局重叠问题，任选其一
//                .statusBarView(view)  //解决状态栏和布局重叠问题，任选其一
//                .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
//                .supportActionBar(true) //支持ActionBar使用
//                .statusBarColorTransform(R.color.orange)  //状态栏变色后的颜色
//                .navigationBarColorTransform(R.color.orange) //导航栏变色后的颜色
//                .barColorTransform(R.color.orange)  //状态栏和导航栏变色后的颜色
//                .removeSupportView(toolbar)  //移除指定view支持
//                .removeSupportAllView() //移除全部view支持
//                .navigationBarEnable(true)   //是否可以修改导航栏颜色，默认为true
//                .navigationBarWithKitkatEnable(true)  //是否可以修改安卓4.4和emui3.1手机导航栏颜色，默认为true
//                .fixMarginAtBottom(true)   //已过时，当xml里使用android:fitsSystemWindows="true"属性时,解决4.4和emui3.1手机底部有时会出现多余空白的问题，默认为false，非必须
//                .addTag("tag")  //给以上设置的参数打标记
//                .getTag("tag")  //根据tag获得沉浸式参数
//                .reset()  //重置所以沉浸式参数
//                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
//                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //单独指定软键盘模式
//                .setOnKeyboardListener(OnKeyboardListener { isPopup, keyboardHeight ->
                      //isPopup为true，软键盘弹出，为false，软键盘关闭
//                })
                .init()
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