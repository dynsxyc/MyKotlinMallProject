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
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import com.jakewharton.rxbinding2.view.RxView
import com.orhanobut.logger.Logger
import com.zhongjiang.kotlin.splash.presenter.splashfragment.SplashFragmentContract
import com.zhongjiang.kotlin.splash.presenter.splashfragment.SplashFragmentPresenter
import com.zhongjiang.youxuan.base.busevent.ActivityRequestCode
import com.zhongjiang.youxuan.base.data.db.SplashAdEntity
import com.zhongjiang.youxuan.base.ext.shieldDoubleClick
import com.zhongjiang.youxuan.base.imageloader.ImageLoaderUtil
import com.zhongjiang.youxuan.base.injection.WindowScreenInfo
import com.zhongjiang.youxuan.base.ui.fragment.BaseMvpFragment
import com.zhongjiang.youxuan.provider.router.NavigationUtil
import com.zhongjiang.youxuan.user.splash.R
import kotlinx.android.synthetic.main.fragment_splash.*
import me.yokeyword.fragmentation.ISupportFragment
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/25.
 */
class SplashFragment : BaseMvpFragment<SplashFragmentPresenter,SplashFragmentContract.Model>(), SplashFragmentContract.View {
    @Inject
    lateinit var screenWidth: WindowScreenInfo
    /**公共实现部分 start*/
    override fun initData() {
        presenter.requestAdInfo("")
        presenter.registerActivityResultEvent {
            Logger.i("registerActivityResultEvent ${it.requestCoder}")
            when(it.requestCoder){
                ActivityRequestCode.REQUEST_WEBSHOW_CODE.requestCode -> {
                    presenter.checkSkip()
                }
            }
        }

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
    /**公共实现部分 end*/

    /**当前业务部分 start*/
    @SuppressLint("AutoDispose")
    override fun onShowAd(adBean: SplashAdEntity) {
        ImageLoaderUtil.displayImage(adBean.imgPathUrl, mSplashFragmentImgAd, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_RESOURCE, object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                presenter.checkSkip()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                presenter.startAdTime(adBean.showTime.toLong())
                return false
            }

        }, screenWidth.width, screenWidth.height * 1080 / 1334)
        RxView.clicks(mSplashFragmentImgAd).shieldDoubleClick {
            //点击广告
            presenter.stopTimeer()
            skipWeb(adBean.imgPageUrl)
        }
        RxView.clicks(mSplashFragmentTvSkip).shieldDoubleClick {
            presenter.checkSkip()
        }
    }

    override fun onRefreshTimer(time: String) {
        Log.i("test1", " onRefreshTimer = ${view == null}")
        if (mSplashFragmentLlTimeer != null) {
            mSplashFragmentLlTimeer.visibility = VISIBLE
        }
        mSplashFragmentTvTime.text = time
    }

    override fun onLoginSuccess() {
        _mActivity.finish()
        NavigationUtil.navigationToMain()
    }

    override fun skipLogin() {
        start(LoginFragment.newInstance(false), ISupportFragment.SINGLETASK)
    }

    override fun skipWeb(webUrl: String) {
        NavigationUtil.navigationToWebShowResult(_mActivity,"http://youx7.youx.mobi/activity/qualitylife?appAreaCode=441723&appUserMobile=15868490449")
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).fitsSystemWindows(false).init()
    }

    /**当前业务部分 end*/

}