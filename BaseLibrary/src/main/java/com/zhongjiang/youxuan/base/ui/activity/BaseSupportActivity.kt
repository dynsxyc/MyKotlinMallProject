package com.zhongjiang.youxuan.base.ui.activity

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.zhongjiang.youxuan.base.common.AppManager
import me.yokeyword.fragmentation.*
import me.yokeyword.fragmentation.anim.FragmentAnimator
import me.yokeyword.fragmentation_swipeback.core.ISwipeBackActivity
import me.yokeyword.fragmentation_swipeback.core.SwipeBackActivityDelegate

/**
 * Created by dyn on 2018/7/13.
 */
abstract class BaseSupportActivity : AppCompatActivity(), ISupportActivity, ISwipeBackActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)
        mDelegate.onCreate(savedInstanceState)
        mSwipeBackDelegate.onCreate(savedInstanceState)
        setSwipeBackEnable(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
        mDelegate.onDestroy()
    }

    val mSwipeBackDelegate by lazy { SwipeBackActivityDelegate(this) }
    val mDelegate by lazy { SupportActivityDelegate(this) }

    override fun getSupportDelegate(): SupportActivityDelegate {
        return mDelegate;
    }

    /**
     * Perform some extra transactions.
     * 额外的事务：自定义Tag，添加SharedElement动画，操作非回退栈Fragment
     */
    override fun extraTransaction(): ExtraTransaction {
        return mDelegate.extraTransaction();
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
        mSwipeBackDelegate.onPostCreate(savedInstanceState)
    }

    /**
     * Note： return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    /**
     * 不建议复写该方法,请使用 {@link #onBackPressedSupport} 代替
     */
    override fun onBackPressed() {
        mDelegate.onBackPressed();
    }

    /**
     * 该方法回调时机为,Activity回退栈内Fragment的数量 小于等于1 时,默认finish Activity
     * 请尽量复写该方法,避免复写onBackPress(),以保证SupportFragment内的onBackPressedSupport()回退事件正常执行
     */
    override fun onBackPressedSupport() {
        mDelegate.onBackPressedSupport()
    }

    /**
     * 加载多个同级根Fragment,类似Wechat, QQ主页的场景
     */
    fun loadMultipleRootFragment(containerId: Int, showPosition: Int, vararg toFragments: ISupportFragment) {
        mDelegate.loadMultipleRootFragment(containerId, showPosition, *toFragments)
    }
    /**
     * 获取设置的全局动画 copy
     *
     * @return FragmentAnimator
     */
    override fun getFragmentAnimator(): FragmentAnimator {
        return mDelegate.getFragmentAnimator();
    }

    /**
     * Set all fragments animation.
     * 设置Fragment内的全局动画
     */
    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator) {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    /**
     * Set all fragments animation.
     * 构建Fragment转场动画
     *
     *
     * 如果是在Activity内实现,则构建的是Activity内所有Fragment的转场动画,
     * 如果是在Fragment内实现,则构建的是该Fragment的转场动画,此时优先级 > Activity的onCreateFragmentAnimator()
     *
     * @return FragmentAnimator对象
     */
    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return mDelegate.onCreateFragmentAnimator()
    }

    /**
     * Causes the Runnable r to be added to the action queue.
     *
     *
     * The runnable will be run after all the previous action has been run.
     *
     *
     * 前面的事务全部执行后 执行该Action
     */
    override fun post(runnable: Runnable) {
        mDelegate.post(runnable)
    }

    /****************************************以下为可选方法(Optional methods) */

    // 选择性拓展其他方法

    fun loadRootFragment(containerId: Int, toFragment: ISupportFragment) {
        mDelegate.loadRootFragment(containerId, toFragment)
    }

    fun start(toFragment: ISupportFragment) {
        mDelegate.start(toFragment)
    }

    /**
     * @param launchMode Same as Activity's LaunchMode.
     */
    fun start(toFragment: ISupportFragment, @ISupportFragment.LaunchMode launchMode: Int) {
        mDelegate.start(toFragment, launchMode)
    }

    /**
     * It is recommended to use [SupportFragment.startWithPopTo].
     *
     * @see .popTo
     * @see .start
     */
    fun startWithPopTo(toFragment: ISupportFragment, targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        mDelegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment)
    }

    /**
     * Pop the fragment.
     */
    fun pop() {
        mDelegate.pop()
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     */
    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment)
    }

    /**
     * If you want to begin another FragmentTransaction immediately after popTo(), use this method.
     * 如果你想在出栈后, 立刻进行FragmentTransaction操作，请使用该方法
     */
    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean, afterPopTransactionRunnable: Runnable) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable)
    }

    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean, afterPopTransactionRunnable: Runnable, popAnim: Int) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim)
    }

    /**
     * 获取栈内的fragment对象
     */
    fun <T : ISupportFragment> findFragment(fragmentClass: Class<T>): T {
        return SupportHelper.findFragment(getSupportFragmentManager(), fragmentClass)
    }

    /******************** SwipeBack Start ************************/
    override fun getSwipeBackLayout(): SwipeBackLayout {
        return mSwipeBackDelegate.swipeBackLayout
    }

    override fun setEdgeLevel(edgeLevel: SwipeBackLayout.EdgeLevel?) {
        mSwipeBackDelegate.setEdgeLevel(edgeLevel)
    }

    override fun setEdgeLevel(widthPixel: Int) {
        mSwipeBackDelegate.setEdgeLevel(widthPixel)
    }

    override fun swipeBackPriority(): Boolean {
        return mSwipeBackDelegate.swipeBackPriority()
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        mSwipeBackDelegate.setSwipeBackEnable(getSwipeBackEnable())
    }

    protected open fun getSwipeBackEnable(): Boolean {
        return true
    }
    /******************** SwipeBack end ************************/

}
