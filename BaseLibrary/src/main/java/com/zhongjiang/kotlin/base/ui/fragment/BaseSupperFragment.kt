package com.zhongjiang.kotlin.base.ui.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import me.yokeyword.fragmentation.*
import me.yokeyword.fragmentation.anim.FragmentAnimator
import me.yokeyword.fragmentation_swipeback.core.ISwipeBackFragment
import me.yokeyword.fragmentation_swipeback.core.SwipeBackFragmentDelegate

/**
 * Created by dyn on 2018/7/13.
 */
abstract class BaseSupperFragment : Fragment(), ISupportFragment, ISwipeBackFragment {
    val mDelegate by lazy { SupportFragmentDelegate(this) }
    val mSwipeBackDelegate by lazy { SwipeBackFragmentDelegate(this) }
    val _mActivity by lazy {
        mDelegate.activity
    }

    override fun getSupportDelegate(): SupportFragmentDelegate {
        return mDelegate;
    }

    /**
     * Perform some extra transactions.
     * 额外的事务：自定义Tag，添加SharedElement动画，操作非回退栈Fragment
     */
    override fun extraTransaction(): ExtraTransaction {
        return mDelegate.extraTransaction();
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity);
        mDelegate?.onAttach(activity);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDelegate?.onCreate(savedInstanceState)
        mSwipeBackDelegate?.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSwipeBackDelegate?.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return mDelegate?.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mDelegate?.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mDelegate?.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mDelegate?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mDelegate?.onPause()
    }

    override fun onDestroyView() {
        mDelegate?.onDestroyView()
        mSwipeBackDelegate?.onDestroyView()
        super.onDestroyView()

    }

    override fun onDestroy() {
        mDelegate?.onDestroy()
        super.onDestroy()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mDelegate?.onHiddenChanged(hidden)
        mSwipeBackDelegate?.onHiddenChanged(hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mDelegate?.setUserVisibleHint(isVisibleToUser)
    }

    override fun enqueueAction(runnable: Runnable?) {
        mDelegate?.enqueueAction(runnable)
    }

    override fun post(runnable: Runnable?) {
        mDelegate?.post(runnable)
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        mDelegate?.onEnterAnimationEnd(savedInstanceState)
    }


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        mDelegate?.onLazyInitView(savedInstanceState)
    }

    override fun onSupportVisible() {
        mDelegate?.onSupportVisible()
    }

    override fun onSupportInvisible() {
        mDelegate?.onSupportInvisible()
    }

    override fun isSupportVisible(): Boolean {
        return mDelegate?.isSupportVisible
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return mDelegate?.onCreateFragmentAnimator()
    }

    override fun getFragmentAnimator(): FragmentAnimator {
        return mDelegate?.fragmentAnimator
    }

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        mDelegate?.fragmentAnimator = fragmentAnimator
    }

    override fun onBackPressedSupport(): Boolean {
        return mDelegate?.onBackPressedSupport()
    }

    override fun setFragmentResult(resultCode: Int, bundle: Bundle?) {
        mDelegate?.setFragmentResult(resultCode, bundle)
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        mDelegate?.onFragmentResult(requestCode, resultCode, data)
    }

    override fun onNewBundle(args: Bundle?) {
        mDelegate?.onNewBundle(args)
    }

    override fun putNewBundle(newBundle: Bundle?) {
        mDelegate?.putNewBundle(newBundle)
    }

    /**
     * 加载多个同级根Fragment,类似Wechat, QQ主页的场景
     */
    fun loadMultipleRootFragment(containerId: Int, showPosition: Int, vararg toFragments: ISupportFragment) {
        mDelegate?.loadMultipleRootFragment(containerId, showPosition, *toFragments)
    }

    /****************************************以下为可选方法(Optional methods)******************************************************/
    // 自定制Support时，可移除不必要的方法

    /**
     * 隐藏软键盘
     */

    protected fun hideSoftInput() {
        mDelegate?.hideSoftInput();
    }

    /**
     * 显示软键盘,调用该方法后,会在onPause时自动隐藏软键盘
     */
    protected fun showSoftInput(view: View) {
        mDelegate?.showSoftInput(view);
    }

    /**
     * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
     *
     * @param containerId 容器id
     * @param toFragment  目标Fragment
     */
    fun loadRootFragment(containerId: Int, toFragment: ISupportFragment) {
        mDelegate?.loadRootFragment(containerId, toFragment);
    }

    fun loadRootFragment(containerId: Int, toFragment: ISupportFragment, addToBackStack: Boolean, allowAnim: Boolean) {
        mDelegate?.loadRootFragment(containerId, toFragment, addToBackStack, allowAnim);
    }

    fun start(toFragment: ISupportFragment) {
        mDelegate?.start(toFragment)
    }

    /**
     * @param launchMode Similar to Activity's LaunchMode.
     */
    fun start(toFragment: ISupportFragment, @ISupportFragment.LaunchMode launchMode: Int) {
        mDelegate?.start(toFragment, launchMode)
    }

    /**
     * Launch an fragment for which you would like a result when it poped.
     */
    fun startForResult(toFragment: ISupportFragment, requestCode: Int) {
        mDelegate?.startForResult(toFragment, requestCode);
    }

    /**
     * Start the target Fragment and pop itself
     */
    fun startWithPop(toFragment: ISupportFragment) {
        mDelegate?.startWithPop(toFragment);
    }

    /**
     * @see #popTo(Class, boolean)
     * +
     * @see #start(ISupportFragment)
     */
    fun startWithPopTo(toFragment: ISupportFragment, targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        mDelegate?.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment);
    }

    fun replaceFragment(toFragment: ISupportFragment, addToBackStack: Boolean) {
        mDelegate.replaceFragment(toFragment, addToBackStack);
    }

    fun pop() {
        mDelegate?.pop();
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     * <p>
     * 出栈到目标fragment
     *
     * @param targetFragmentClass   目标fragment
     * @param includeTargetFragment 是否包含该fragment
     */
    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        mDelegate?.popTo(targetFragmentClass, includeTargetFragment);
    }

    /**
     * 获取栈内的fragment对象
     */
    fun <T : ISupportFragment> findChildFragment(fragmentClass: Class<T>): T {
        return SupportHelper.findFragment(getChildFragmentManager(), fragmentClass);
    }

    /*********************** SwipeBack  start ********************************/
    override fun getSwipeBackLayout(): SwipeBackLayout {
        return mSwipeBackDelegate?.swipeBackLayout
    }

    override fun attachToSwipeBack(view: View?): View {
        return mSwipeBackDelegate?.attachToSwipeBack(view)
    }

    override fun setEdgeLevel(edgeLevel: SwipeBackLayout.EdgeLevel?) {
        mSwipeBackDelegate?.setEdgeLevel(edgeLevel)
    }

    override fun setEdgeLevel(widthPixel: Int) {
        mSwipeBackDelegate?.setEdgeLevel(widthPixel)
    }

    override fun setParallaxOffset(offset: Float) {
        mSwipeBackDelegate?.setParallaxOffset(offset)
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        mSwipeBackDelegate?.setSwipeBackEnable(getSwipeBackEnable())
    }

    protected open fun getSwipeBackEnable(): Boolean {
        return true
    }
    /*********************** SwipeBack  end ********************************/
}