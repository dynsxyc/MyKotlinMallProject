package com.zhongjiang.kotlin.splash.ui.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jakewharton.rxbinding2.view.RxView
import com.zhongjiang.kotlin.base.data.db.SplashAdEntity
import com.zhongjiang.kotlin.base.ext.shieldDoubleClick
import com.zhongjiang.kotlin.base.imageloader.ImageLoaderUtil
import com.zhongjiang.kotlin.base.injection.WindowScreenInfo
import com.zhongjiang.kotlin.base.ui.fragment.BaseMvpFragment
import com.zhongjiang.kotlin.splash.R
import com.zhongjiang.kotlin.splash.presenter.SplashFragmentPresenter
import com.zhongjiang.kotlin.splash.presenter.contract.SplashFragmentContract
import kotlinx.android.synthetic.main.fragment_splash.*
import me.yokeyword.fragmentation.ISupportFragment
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

    override fun onSupportVisible() {
        super.onSupportVisible()
        mPresenter.checkTimerDisposable()
    }
    /**公共实现部分 end*/

    /**当前业务部分 start*/
    @SuppressLint("AutoDispose")
    override fun onShowAd(adBean: SplashAdEntity) {
        ImageLoaderUtil.displayImage(adBean.imgPathUrl, mSplashFragmentImgAd, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_RESOURCE, object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                mPresenter.checkSkip()
                return false;
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                mPresenter.startTime(adBean.showTime.toLong())
                return false
            }

        }, screenWidth.width, screenWidth.height * 1080 / 1334)
        RxView.clicks(mSplashFragmentImgAd).shieldDoubleClick {
            //点击广告
            skipWeb(adBean.imgPageUrl)
        }
        RxView.clicks(mSplashFragmentTvSkip).shieldDoubleClick {
            mPresenter.checkSkip()
        }
    }

    override fun onRefreshTimer(userInfo: String) {
        Log.i("test1", " onRefreshTimer = ${view == null}")
        if (mSplashFragmentLlTimeer != null) {
            mSplashFragmentLlTimeer.visibility = VISIBLE
        }
        mSplashFragmentTvTime.text = userInfo
    }

    override fun skipMain() {
        start(LoginFragment.newInstance(false), ISupportFragment.SINGLETASK)
    }

    override fun skipLogin() {
        start(LoginFragment.newInstance(false), ISupportFragment.SINGLETASK)
    }

    override fun skipWeb(webUrl: String) {

    }
    /**当前业务部分 end*/

}