package com.zhongjiang.youxuan.base.utils

import android.Manifest.permission.EXPAND_STATUS_BAR
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.LinearLayout
import androidx.annotation.*
import androidx.drawerlayout.widget.DrawerLayout
import java.util.regex.Pattern


/**
 * 状态栏透明
 * Created by SCWANG on 2016/10/26.
 */

class StatusBarUtil {
    companion object {
        private var DEFAULT_COLOR = 0xffffff
        private var DEFAULT_ALPHA = 0f
        //Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 0.2f : 0.3f;
        private const val MIN_API = 19

        /** 判断是否Flyme4以上  */
        private val isFlyme4Later: Boolean
            get() = (Build.FINGERPRINT.contains("Flyme_OS_4")
                    || Build.VERSION.INCREMENTAL.contains("Flyme_OS_4")
                    || Pattern.compile("Flyme OS [4|5]", Pattern.CASE_INSENSITIVE).matcher(Build.DISPLAY).find())

        /** 判断是否为MIUI6以上  */
        private val isMIUI6Later: Boolean
            get() {
                try {
                    val clz = Class.forName("android.os.SystemProperties")
                    val mtd = clz.getMethod("get", String::class.java)
                    var `val` = mtd.invoke(null, "ro.miui.ui.version.name") as String
                    `val` = `val`.replace("[vV]".toRegex(), "")
                    val version = Integer.parseInt(`val`)
                    return version >= 6
                } catch (e: Exception) {
                    return false
                }

            }

        fun immersive(activity: Activity, color: Int = DEFAULT_COLOR, @FloatRange(from = 0.0, to = 1.0) alpha: Float = DEFAULT_ALPHA) {
            immersive(activity.window, color, alpha)
        }

        fun immersive(activity: Activity, color: Int) {
            immersive(activity.window, color, 1f)
        }

        fun immersive(window: Window) {
            immersive(window, DEFAULT_COLOR, DEFAULT_ALPHA)
        }

        fun immersive(activity: Activity) {
            immersive(activity, DEFAULT_COLOR, DEFAULT_ALPHA)
        }

        fun immersive(window: Window, color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float = 1f) {
            if (Build.VERSION.SDK_INT >= 21) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = mixtureColor(color, alpha)

                var systemUiVisibility = window.decorView.systemUiVisibility
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.decorView.systemUiVisibility = systemUiVisibility
            } else if (Build.VERSION.SDK_INT >= 19) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                setTranslucentView(window.decorView as ViewGroup, color,alpha)
            } else if (Build.VERSION.SDK_INT >= MIN_API && Build.VERSION.SDK_INT > 16) {
                var systemUiVisibility = window.decorView.systemUiVisibility
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.decorView.systemUiVisibility = systemUiVisibility
            }
        }

        fun darkMode(activity: Activity, dark: Boolean) {
            if (isFlyme4Later) {
                darkModeForFlyme4(activity.window, dark)
            } else if (isMIUI6Later) {
                darkModeForMIUI6(activity.window, dark)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                darkModeForM(activity.window, dark)
            }
        }

        /** 设置状态栏darkMode,字体颜色及icon变黑(目前支持MIUI6以上,Flyme4以上,Android M以上)  */
        fun darkMode(activity: Activity) {
            darkMode(activity.window, DEFAULT_COLOR, DEFAULT_ALPHA)
        }

        fun darkMode(activity: Activity, color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
            darkMode(activity.window, color, alpha)
        }

        /** 设置状态栏darkMode,字体颜色及icon变黑(目前支持MIUI6以上,Flyme4以上,Android M以上)  */
        fun darkMode(window: Window, color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
            if (isFlyme4Later) {
                darkModeForFlyme4(window, true)
                immersive(window, color, DEFAULT_ALPHA)
            } else if (isMIUI6Later) {
                darkModeForMIUI6(window, true)
                immersive(window, color, DEFAULT_ALPHA)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                darkModeForM(window, true)
                immersive(window, color, DEFAULT_ALPHA)
            } else if (Build.VERSION.SDK_INT >= 19) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                setTranslucentView(window.decorView as ViewGroup,  color,alpha)
            } else {
                immersive(window, color, DEFAULT_ALPHA)
            }
        }

        //------------------------->

        /** android 6.0设置字体颜色  */
        @RequiresApi(Build.VERSION_CODES.M)
        private fun darkModeForM(window: Window, dark: Boolean) {
            var systemUiVisibility = window.decorView.systemUiVisibility
            if (dark) {
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                systemUiVisibility = systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            window.decorView.systemUiVisibility = systemUiVisibility
        }

        /**
         * 设置Flyme4+的darkMode,darkMode时候字体颜色及icon变黑
         * http://open-wiki.flyme.cn/index.php?title=Flyme%E7%B3%BB%E7%BB%9FAPI
         */
        fun darkModeForFlyme4(window: Window?, dark: Boolean): Boolean {
            var result = false
            if (window != null) {
                try {
                    val e = window.attributes
                    val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                    val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
                    darkFlag.isAccessible = true
                    meizuFlags.isAccessible = true
                    val bit = darkFlag.getInt(null)
                    var value = meizuFlags.getInt(e)
                    if (dark) {
                        value = value or bit
                    } else {
                        value = value and bit.inv()
                    }

                    meizuFlags.setInt(e, value)
                    window.attributes = e
                    result = true
                } catch (var8: Exception) {
                    Log.e("StatusBar", "darkIcon: failed")
                }

            }

            return result
        }

        /**
         * 设置MIUI6+的状态栏是否为darkMode,darkMode时候字体颜色及icon变黑
         * http://dev.xiaomi.com/doc/p=4769/
         */
        fun darkModeForMIUI6(window: Window, darkmode: Boolean): Boolean {
            val clazz = window.javaClass
            try {
                var darkModeFlag: Int
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                extraFlagField.invoke(window, if (darkmode) darkModeFlag else 0, darkModeFlag)
                return true
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }

        }
        //</editor-fold>


        /** 增加View的paddingTop,增加的值为状态栏高度  */
        fun setPadding(context: Context, view: View) {
                view.setPadding(view.paddingLeft, view.paddingTop + getStatusBarHeight(context),
                        view.paddingRight, view.paddingBottom)
        }

        /** 增加View的paddingTop,增加的值为状态栏高度  */
        fun setMarginTop(context: Context, view: View) {
                (view.layoutParams as ViewGroup.MarginLayoutParams).topMargin = getStatusBarHeight(context)
        }

        /** 增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度) */
        fun setPaddingSmart(context: Context, view: View) {
                val lp = view.layoutParams
                if (lp != null && lp.height > 0) {
                    lp.height += getStatusBarHeight(context)//增高
                }
                view.setPadding(view.paddingLeft, view.paddingTop + getStatusBarHeight(context),
                        view.paddingRight, view.paddingBottom)
        }

        /** 增加View的高度以及paddingTop,增加的值为状态栏高度.一般是在沉浸式全屏给ToolBar用的  */
        fun setHeightAndPadding(context: Context, view: View) {
                val lp = view.layoutParams
                lp.height += getStatusBarHeight(context)//增高
                view.setPadding(view.paddingLeft, view.paddingTop + getStatusBarHeight(context),
                        view.paddingRight, view.paddingBottom)
        }

        /** 增加View上边距（MarginTop）一般是给高度为 WARP_CONTENT 的小控件用的 */
        fun setMargin(context: Context, view: View) {
                val lp = view.layoutParams
                if (lp is ViewGroup.MarginLayoutParams) {
                    lp.topMargin += getStatusBarHeight(context)//增高
                }
                view.layoutParams = lp
        }

        /**
         * 创建假的透明栏
         */
        fun setTranslucentView(container: ViewGroup, color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
            if (Build.VERSION.SDK_INT >= 19) {
                val mixtureColor = mixtureColor(color, alpha)
                var translucentView: View? = container.findViewById(android.R.id.custom)
                if (translucentView == null && mixtureColor != 0) {
                    translucentView = View(container.context)
                    translucentView.id = android.R.id.custom
                    val lp = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(container.context))
                    container.addView(translucentView, lp)
                }
                translucentView?.setBackgroundColor(mixtureColor)
            }
        }

        private fun mixtureColor(color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
            val a = if (color and -0x1000000 == 0) 0xff else color.ushr(24)
            return color and 0x00ffffff or ((a * alpha).toInt() shl 24)
        }

        /** 获取状态栏高度  */
        fun getStatusBarHeight(context: Context): Int {
            var result = 24
            val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resId > 0) {
                result = context.resources.getDimensionPixelSize(resId)
            } else {
                result = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        result.toFloat(), Resources.getSystem().displayMetrics).toInt()
            }
            return result
        }

        /**-------------------自己加的-------------------------------------*/

        var DEFAULT_ALPHA_M = 112
        var TAG_COLOR = "TAG_COLOR"
        var TAG_ALPHA = "TAG_ALPHA"
        var TAG_OFFSET = "TAG_OFFSET"
        var KEY_OFFSET = -123

        /**
         * 获取状态栏高度
         *
         * @return the status bar's height
         */
        fun getStatusBarHeight(): Int {
            val resources = Resources.getSystem()
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            return resources.getDimensionPixelSize(resourceId)
        }

        /**
         * 设置状态栏可见性
         *
         * @param activity  The activity.
         * @param isVisible True to set status bar visible, false otherwise.
         */
        fun setStatusBarVisibility(@NonNull activity: Activity,
                                   isVisible: Boolean) {
            setStatusBarVisibility(activity.window, isVisible)
        }

        /**
         * 设置状态栏可见性
         *
         * @param window    The window.
         * @param isVisible True to set status bar visible, false otherwise.
         */
        fun setStatusBarVisibility(@NonNull window: Window,
                                   isVisible: Boolean) {
            if (isVisible) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                showColorView(window)
                showAlphaView(window)
                addMarginTopEqualStatusBarHeight(window)
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                hideColorView(window)
                hideAlphaView(window)
                subtractMarginTopEqualStatusBarHeight(window)
            }
        }

        /**
         * 获取状态栏是否可见
         *
         * @param activity The activity.
         * @return `true`: yes<br></br>`false`: no
         */
        fun isStatusBarVisible(@NonNull activity: Activity): Boolean {
            val flags = activity.window.attributes.flags
            return flags and WindowManager.LayoutParams.FLAG_FULLSCREEN == 0
        }

        /**
         * 设置状态栏的灯光模式。
         *
         * @param activity    The activity.
         * @param isLightMode True to set status bar light mode, false otherwise.
         */
        fun setStatusBarLightMode(@NonNull activity: Activity,
                                  isLightMode: Boolean) {
            setStatusBarLightMode(activity.window, isLightMode)
        }

        /**
         * 设置状态栏的灯光模式。
         *
         * @param window      The window.
         * @param isLightMode True to set status bar light mode, false otherwise.
         */
        fun setStatusBarLightMode(@NonNull window: Window,
                                  isLightMode: Boolean) {
                val decorView = window.decorView
                if (decorView != null) {
                    var vis = decorView.systemUiVisibility
                    if (isLightMode) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        vis = vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        vis = vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    }
                    decorView.systemUiVisibility = vis
                }
        }

        /**
         * 添加上边距大小等于状态栏的视图高度。
         *
         * @param view The view.
         */
        fun addMarginTopEqualStatusBarHeight(@NonNull view: View) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            view.tag = TAG_OFFSET
            val haveSetOffset = view.getTag(KEY_OFFSET)
            if (haveSetOffset != null && haveSetOffset as Boolean) return
            val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(layoutParams.leftMargin,
                    layoutParams.topMargin + getStatusBarHeight(),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin)
            view.setTag(KEY_OFFSET, true)
        }

        /**
         * 减去上边距大小等于状态栏的视图高度。
         *
         * @param view The view.
         */
        fun subtractMarginTopEqualStatusBarHeight(@NonNull view: View) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            val haveSetOffset = view.getTag(KEY_OFFSET)
            if (haveSetOffset == null || !(haveSetOffset as Boolean)) return
            val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(layoutParams.leftMargin,
                    layoutParams.topMargin - getStatusBarHeight(),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin)
            view.setTag(KEY_OFFSET, false)
        }

        private fun addMarginTopEqualStatusBarHeight(window: Window) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            val withTag = window.decorView.findViewWithTag<View>(TAG_OFFSET) ?: return
            addMarginTopEqualStatusBarHeight(withTag)
        }

        private fun subtractMarginTopEqualStatusBarHeight(window: Window) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            val withTag = window.decorView.findViewWithTag<View>(TAG_OFFSET) ?: return
            subtractMarginTopEqualStatusBarHeight(withTag)
        }

        /**
         * 设置状态栏的颜色。
         *
         * @param activity The activity.
         * @param color    The status bar's color.
         */
        fun setStatusBarColor(@NonNull activity: Activity,
                              @ColorInt color: Int) {
            setStatusBarColor(activity, color, DEFAULT_ALPHA_M, false)
        }

        /**
         * 设置状态栏的颜色。
         *
         * @param activity The activity.
         * @param color    The status bar's color.
         * @param alpha    The status bar's alpha which isn't the same as alpha in the color.
         */
        fun setStatusBarColor(@NonNull activity: Activity,
                              @ColorInt color: Int,
                              @androidx.annotation.IntRange(from = 0, to = 255) alpha: Int) {
            setStatusBarColor(activity, color, alpha, false)
        }

        /**
         * Set the status bar's color.
         *
         * @param activity The activity.
         * @param color    The status bar's color.
         * @param alpha    The status bar's alpha which isn't the same as alpha in the color.
         * @param isDecor  True to add fake status bar in DecorView,
         * false to add fake status bar in ContentView.
         */
        fun setStatusBarColor(@NonNull activity: Activity,
                              @ColorInt color: Int,
                              @androidx.annotation.IntRange(from = 0, to = 255) alpha: Int,
                              isDecor: Boolean) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            hideAlphaView(activity)
            transparentStatusBar(activity)
            addStatusBarColor(activity, color, alpha, isDecor)
        }

        /**
         * Set the status bar's color.
         *
         * @param fakeStatusBar The fake status bar view.
         * @param color         The status bar's color.
         */
        fun setStatusBarColor(@NonNull fakeStatusBar: View,
                              @ColorInt color: Int) {
            setStatusBarColor(fakeStatusBar, color, DEFAULT_ALPHA_M)
        }

        /**
         * Set the status bar's color.
         *
         * @param fakeStatusBar The fake status bar view.
         * @param color         The status bar's color.
         * @param alpha         The status bar's alpha which isn't the same as alpha in the color.
         */
        fun setStatusBarColor(@NonNull fakeStatusBar: View,
                              @ColorInt color: Int,
                              @androidx.annotation.IntRange(from = 0, to = 255) alpha: Int) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            fakeStatusBar.visibility = View.VISIBLE
            transparentStatusBar(fakeStatusBar.context as Activity)
            val layoutParams = fakeStatusBar.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = getStatusBarHeight()
            fakeStatusBar.setBackgroundColor(getStatusBarColor(color, alpha))
        }

        /**
         * Set the status bar's alpha.
         *
         * @param activity The activity.
         */
        fun setStatusBarAlpha(@NonNull activity: Activity) {
            setStatusBarAlpha(activity, DEFAULT_ALPHA_M, false)
        }
        /**
         * Set the status bar's alpha.
         *
         * @param activity The activity.
         */
        fun setStatusBarAlpha(@NonNull activity: Activity,isDecor: Boolean) {
            setStatusBarAlpha(activity, DEFAULT_ALPHA_M, isDecor)
        }

        /**
         * Set the status bar's alpha.
         *
         * @param activity The activity.
         * @param alpha    The status bar's alpha.
         */
        fun setStatusBarAlpha(@NonNull activity: Activity,
                              @androidx.annotation.IntRange(from = 0, to = 255) alpha: Int) {
            setStatusBarAlpha(activity, alpha, false)
        }

        /**
         * Set the status bar's alpha.
         *
         * @param activity The activity.
         * @param alpha    The status bar's alpha.
         * @param isDecor  True to add fake status bar in DecorView,
         * false to add fake status bar in ContentView.
         */
        fun setStatusBarAlpha(
                @NonNull activity: Activity,
                @androidx.annotation.IntRange(from = 0, to = 255) alpha: Int,
                isDecor: Boolean
        ) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            hideColorView(activity)
            transparentStatusBar(activity)
            addStatusBarAlpha(activity, alpha, isDecor)
        }

        /**
         * Set the status bar's alpha.
         *
         * @param fakeStatusBar The fake status bar view.
         */
        fun setStatusBarAlpha(@NonNull fakeStatusBar: View) {
            setStatusBarAlpha(fakeStatusBar, DEFAULT_ALPHA_M)
        }

        /**
         * Set the status bar's alpha.
         *
         * @param fakeStatusBar The fake status bar view.
         * @param alpha         The status bar's alpha.
         */
        fun setStatusBarAlpha(@NonNull fakeStatusBar: View,
                              @androidx.annotation.IntRange(from = 0, to = 255) alpha: Int) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            fakeStatusBar.visibility = View.VISIBLE
            transparentStatusBar(fakeStatusBar.context as Activity)
            val layoutParams = fakeStatusBar.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = getStatusBarHeight()
            fakeStatusBar.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
        }

        /**
         * 设置自定义状态栏。
         *
         * @param fakeStatusBar The fake status bar view.
         */
        fun setStatusBarCustom(@NonNull fakeStatusBar: View) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            fakeStatusBar.visibility = View.VISIBLE
            transparentStatusBar(fakeStatusBar.context as Activity)
            val layoutParams = fakeStatusBar.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = getStatusBarHeight()
        }

        /**
         * Set the status bar's color for DrawerLayout.
         *
         * DrawLayout must add `android:fitsSystemWindows="true"`
         *
         * @param activity      The activity.
         * @param drawer        The DrawLayout.
         * @param fakeStatusBar The fake status bar view.
         * @param color         The status bar's color.
         * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
         */
        fun setStatusBarColor4Drawer(@NonNull activity: Activity,
                                     @NonNull drawer: DrawerLayout,
                                     @NonNull fakeStatusBar: View,
                                     @ColorInt color: Int,
                                     isTop: Boolean) {
            setStatusBarColor4Drawer(activity, drawer, fakeStatusBar, color, DEFAULT_ALPHA_M, isTop)
        }

        /**
         * Set the status bar's color for DrawerLayout.
         *
         * DrawLayout must add `android:fitsSystemWindows="true"`
         *
         * @param activity      The activity.
         * @param drawer        The DrawLayout.
         * @param fakeStatusBar The fake status bar view.
         * @param color         The status bar's color.
         * @param alpha         The status bar's alpha which isn't the same as alpha in the color.
         * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
         */
        fun setStatusBarColor4Drawer(@NonNull activity: Activity,
                                     @NonNull drawer: DrawerLayout,
                                     @NonNull fakeStatusBar: View,
                                     @ColorInt color: Int,
                                     @androidx.annotation.IntRange(from = 0, to = 255) alpha: Int,
                                     isTop: Boolean) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            drawer.fitsSystemWindows = false
            transparentStatusBar(activity)
            setStatusBarColor(fakeStatusBar, color, if (isTop) alpha else 0)
            var i = 0
            val len = drawer.childCount
            while (i < len) {
                drawer.getChildAt(i).fitsSystemWindows = false
                i++
            }
            if (isTop) {
                hideAlphaView(activity)
            } else {
                addStatusBarAlpha(activity, alpha, false)
            }
        }

        /**
         * Set the status bar's alpha for DrawerLayout.
         *
         * DrawLayout must add `android:fitsSystemWindows="true"`
         *
         * @param activity      The activity.
         * @param drawer        drawerLayout
         * @param fakeStatusBar The fake status bar view.
         * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
         */
        fun setStatusBarAlpha4Drawer(@NonNull activity: Activity,
                                     @NonNull drawer: DrawerLayout,
                                     @NonNull fakeStatusBar: View,
                                     isTop: Boolean) {
            setStatusBarAlpha4Drawer(activity, drawer, fakeStatusBar, DEFAULT_ALPHA_M, isTop)
        }

        /**
         * 为DrawerLayout设置状态栏的alpha。
         *
         * DrawLayout must add `android:fitsSystemWindows="true"`
         *
         * @param activity      The activity.
         * @param drawer        drawerLayout
         * @param fakeStatusBar The fake status bar view.
         * @param alpha         The status bar's alpha.
         * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
         */
        fun setStatusBarAlpha4Drawer(@NonNull activity: Activity,
                                     @NonNull drawer: DrawerLayout,
                                     @NonNull fakeStatusBar: View,
                                     @androidx.annotation.IntRange(from = 0, to = 255) alpha: Int,
                                     isTop: Boolean) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            drawer.fitsSystemWindows = false
            transparentStatusBar(activity)
            setStatusBarAlpha(fakeStatusBar, if (isTop) alpha else 0)
            var i = 0
            val len = drawer.childCount
            while (i < len) {
                drawer.getChildAt(i).fitsSystemWindows = false
                i++
            }
            if (isTop) {
                hideAlphaView(activity)
            } else {
                addStatusBarAlpha(activity, alpha, false)
            }
        }

        private fun addStatusBarColor(activity: Activity,
                                      color: Int,
                                      alpha: Int,
                                      isDecor: Boolean) {
            val parent = if (isDecor)
                activity.window.decorView as ViewGroup
            else
                activity.findViewById<View>(android.R.id.content) as ViewGroup
            val fakeStatusBarView = parent.findViewWithTag<View>(TAG_COLOR)
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(getStatusBarColor(color, alpha))
            } else {
                parent.addView(createColorStatusBarView(activity, color, alpha))
            }
        }

        private fun addStatusBarAlpha(activity: Activity,
                                      alpha: Int,
                                      isDecor: Boolean) {
            val parent = if (isDecor)
                activity.window.decorView as ViewGroup
            else
                activity.findViewById<View>(android.R.id.content) as ViewGroup
            val fakeStatusBarView = parent.findViewWithTag<View>(TAG_ALPHA)
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
            } else {
                parent.addView(createAlphaStatusBarView(activity, alpha))
            }
        }

        private fun hideColorView(activity: Activity) {
            hideColorView(activity.window)
        }

        private fun hideAlphaView(activity: Activity) {
            hideAlphaView(activity.window)
        }

        private fun hideColorView(window: Window) {
            val decorView = window.decorView as ViewGroup
            val fakeStatusBarView = decorView.findViewWithTag<View>(TAG_COLOR) ?: return
            fakeStatusBarView.visibility = View.GONE
        }

        private fun hideAlphaView(window: Window) {
            val decorView = window.decorView as ViewGroup
            val fakeStatusBarView = decorView.findViewWithTag<View>(TAG_ALPHA) ?: return
            fakeStatusBarView.visibility = View.GONE
        }

        private fun showColorView(window: Window) {
            val decorView = window.decorView as ViewGroup
            val fakeStatusBarView = decorView.findViewWithTag<View>(TAG_COLOR) ?: return
            fakeStatusBarView.visibility = View.VISIBLE
        }

        private fun showAlphaView(window: Window) {
            val decorView = window.decorView as ViewGroup
            val fakeStatusBarView = decorView.findViewWithTag<View>(TAG_ALPHA) ?: return
            fakeStatusBarView.visibility = View.VISIBLE
        }

        private fun getStatusBarColor(color: Int, alpha: Int): Int {
            if (alpha == 0) return color
            val a = 1 - alpha / 255f
            var red = color shr 16 and 0xff
            var green = color shr 8 and 0xff
            var blue = color and 0xff
            red = (red * a + 0.5).toInt()
            green = (green * a + 0.5).toInt()
            blue = (blue * a + 0.5).toInt()
            return Color.argb(255, red, green, blue)
        }

        private fun createColorStatusBarView(context: Context,
                                             color: Int,
                                             alpha: Int): View {
            val statusBarView = View(context)
            statusBarView.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight())
            statusBarView.setBackgroundColor(getStatusBarColor(color, alpha))
            statusBarView.tag = TAG_COLOR
            return statusBarView
        }

        private fun createAlphaStatusBarView(context: Context, alpha: Int): View {
            val statusBarView = View(context)
            statusBarView.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight())
            statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
            statusBarView.tag = TAG_ALPHA
            return statusBarView
        }

        private fun transparentStatusBar(activity: Activity) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            val window = activity.window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                val option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                window.decorView.systemUiVisibility = option
                window.statusBarColor = Color.TRANSPARENT
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }

        ///////////////////////////////////////////////////////////////////////////
        // action bar
        ///////////////////////////////////////////////////////////////////////////

        /**
         * 返回操作栏的高度。
         *
         * @return the action bar's height
         */
        fun getActionBarHeight(context: Context): Int {
            val tv = TypedValue()
            return if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                TypedValue.complexToDimensionPixelSize(
                        tv.data, context.getResources().getDisplayMetrics()
                )
            } else 0
        }

        ///////////////////////////////////////////////////////////////////////////
        // notification bar
        ///////////////////////////////////////////////////////////////////////////

        /**
         * 设置通知栏的可见性
         *
         * Must hold
         * `<uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />`
         *
         * @param isVisible True to set notification bar visible, false otherwise.
         */
        @RequiresPermission(EXPAND_STATUS_BAR)
        fun setNotificationBarVisibility(context: Context,isVisible: Boolean) {
            val methodName: String
            if (isVisible) {
                methodName = if (Build.VERSION.SDK_INT <= 16) "expand" else "expandNotificationsPanel"
            } else {
                methodName = if (Build.VERSION.SDK_INT <= 16) "collapse" else "collapsePanels"
            }
            invokePanels(context,methodName)
        }

        private fun invokePanels(context: Context,methodName: String) {
            try {
                @SuppressLint("WrongConstant")
                val service = context.getSystemService("statusbar")
                @SuppressLint("PrivateApi")
                val statusBarManager = Class.forName("android.app.StatusBarManager")
                val expand = statusBarManager.getMethod(methodName)
                expand.invoke(service)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        ///////////////////////////////////////////////////////////////////////////
        // 导航栏
        ///////////////////////////////////////////////////////////////////////////

        /**
         *返回导航栏的高度
         *
         * @return the navigation bar's height
         */
        fun getNavBarHeight(): Int {
            val res = Resources.getSystem()
            val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
            return if (resourceId != 0) {
                res.getDimensionPixelSize(resourceId)
            } else {
                0
            }
        }

        /**
         * 设置导航栏的可见性。
         *
         * @param activity  The activity.
         * @param isVisible True to set navigation bar visible, false otherwise.
         */
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        fun setNavBarVisibility(@NonNull activity: Activity, isVisible: Boolean) {
            setNavBarVisibility(activity.window, isVisible)
        }

        /**
         * 设置导航栏的可见性。
         *
         * @param window    The window.
         * @param isVisible True to set navigation bar visible, false otherwise.
         */
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun setNavBarVisibility(@NonNull window: Window, isVisible: Boolean) {
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            val decorView = window.decorView
            if (isVisible) {
                decorView.systemUiVisibility = decorView.systemUiVisibility and uiOptions.inv()
            } else {
                decorView.systemUiVisibility = decorView.systemUiVisibility or uiOptions
            }
        }

        /**
         * 返回导航栏是否可见。
         *
         * @param activity The activity.
         * @return `true`: yes<br></br>`false`: no
         */
        fun isNavBarVisible(@NonNull activity: Activity): Boolean {
            return isNavBarVisible(activity.window)
        }

        /**
         * Return whether the navigation bar visible.
         *
         * @param window The window.
         * @return `true`: yes<br></br>`false`: no
         */
        fun isNavBarVisible(@NonNull window: Window): Boolean {
            val decorView = window.decorView
            val visibility = decorView.systemUiVisibility
            return visibility and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION == 0
        }

        /**
         * 设置导航栏的颜色。
         *
         * @param activity The activity.
         * @param color    The navigation bar's color.
         */
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun setNavBarColor(@NonNull activity: Activity, @ColorInt color: Int) {
            setNavBarColor(activity.window, color)
        }

        /**
         * Set the navigation bar's color.
         *
         * @param window The window.
         * @param color  The navigation bar's color.
         */
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun setNavBarColor(@NonNull window: Window, @ColorInt color: Int) {
            window.navigationBarColor = color
        }

        /**
         * 返回导航栏的颜色。
         *
         * @param activity The activity.
         * @return the color of navigation bar
         */
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun getNavBarColor(@NonNull activity: Activity): Int {
            return getNavBarColor(activity.window)
        }

        /**
         * 返回导航栏的颜色。
         *
         * @param window The window.
         * @return the color of navigation bar
         */
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun getNavBarColor(@NonNull window: Window): Int {
            return window.navigationBarColor
        }

        /**
         * 返回导航栏是否可见。
         *
         * @return `true`: yes<br></br>`false`: no
         */
        fun isSupportNavBar(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

                val display = wm.defaultDisplay
                val size = Point()
                val realSize = Point()
                display.getSize(size)
                display.getRealSize(realSize)
                return realSize.y !== size.y || realSize.x !== size.x
            }
            val menu = ViewConfiguration.get(context).hasPermanentMenuKey()
            val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            return !menu && !back
        }

    }
}
