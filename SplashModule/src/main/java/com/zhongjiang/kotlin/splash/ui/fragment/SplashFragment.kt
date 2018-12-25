package com.zhongjiang.kotlin.splash.ui.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jakewharton.rxbinding2.view.RxView
import com.zhongjiang.kotlin.base.imageloader.ImageLoaderUtil
import com.zhongjiang.kotlin.base.injection.WindowScreenInfo
import com.zhongjiang.kotlin.base.ui.fragment.BaseMvpFragment
import com.zhongjiang.kotlin.splash.R
import com.zhongjiang.kotlin.splash.presenter.SplashFragmentPresenter
import com.zhongjiang.kotlin.splash.presenter.contract.SplashFragmentContract
import kotlinx.android.synthetic.main.fragment_splash.*
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/25.
 */
class SplashFragment : BaseMvpFragment<SplashFragmentPresenter>(), SplashFragmentContract.View {
    @Inject
    lateinit var screenWidth: WindowScreenInfo
    /**公共实现部分 start*/
    override fun initData() {
        mPresenter.requestAdInfo("")
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_splash
    }
    override fun initView() {
        mSplashFragmentImgAd.layoutParams.width = screenWidth.width
        mSplashFragmentImgAd.layoutParams.height = screenWidth.height * 1080 / 1334
    }
    companion object {
        fun newInstance(): SplashFragment {
            val args = Bundle()
            val fragment = SplashFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getSwipeBackEnable(): Boolean {
        return false
    }

    override fun onResume() {
        super.onResume()
        mPresenter.checkTimerDisposable()
    }
    /**公共实现部分 end*/

    /**当前业务部分 start*/
    @SuppressLint("AutoDispose")
    override fun onShowAd(adImgPathUrl: String) {
        ImageLoaderUtil.displayImage(adImgPathUrl, mSplashFragmentImgAd, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_RESOURCE, object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                return false;
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false;
            }

        }, screenWidth.width, screenWidth.height * 1080 / 1334)
        RxView.clicks(mSplashFragmentImgAd)
                .throttleFirst(800, TimeUnit.MILLISECONDS)
                .subscribe {
                    activity!!.toast("测试点击")
                    Log.i("test1", "-------------")
                }
    }

    override fun onRefreshTimer(userInfo: String) {
        mSplashFragmentTvTime.text = userInfo
    }
    override fun skipMain() {

    }

    override fun skipLogin() {
        replaceFragment(LoginFragment.newInstance(),false)
    }

    override fun skipWeb() {

    }
    /**当前业务部分 end*/

}