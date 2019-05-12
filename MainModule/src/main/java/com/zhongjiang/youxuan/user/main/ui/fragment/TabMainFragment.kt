package com.zhongjiang.kotlin.splash.ui.fragment

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jakewharton.rxbinding2.view.RxView
import com.orhanobut.logger.Logger
import com.zhongjiang.kotlin.splash.presenter.splashfragment.TabMainFragmentContract
import com.zhongjiang.kotlin.splash.presenter.splashfragment.TabMainFragmentPresenter
import com.zhongjiang.youxuan.base.busevent.ActivityRequestCode
import com.zhongjiang.youxuan.base.data.db.SplashAdEntity
import com.zhongjiang.youxuan.base.ext.shieldDoubleClick
import com.zhongjiang.youxuan.base.imageloader.ImageLoaderUtil
import com.zhongjiang.youxuan.base.injection.WindowScreenInfo
import com.zhongjiang.youxuan.base.ui.fragment.BaseMvpFragment
import com.zhongjiang.youxuan.base.utils.BaiDuUtils
import com.zhongjiang.youxuan.provider.router.NavigationUtil
import com.zhongjiang.youxuan.user.main.R
import kotlinx.android.synthetic.main.fragment_splash.*
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/25.
 */
class TabMainFragment : BaseMvpFragment<TabMainFragmentPresenter>(), TabMainFragmentContract.View {
    @Inject
    lateinit var screenWidth: WindowScreenInfo
    @Inject
    lateinit var baiDuUtils: BaiDuUtils

    /**公共实现部分 start*/
    override fun initData() {
        mPresenter.requestAdInfo("")
        mPresenter.registerActivityResultEvent {
            when (it.requestCoder) {
                ActivityRequestCode.REQUEST_WEBSHOW_CODE.requestCode -> {
                    mPresenter.checkSkip()
                }
            }
        }
        baiDuUtils?.let {
            it.start(null)

        }

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_splash
    }

    override fun initView() {
        mSplashFragmentImgAd.layoutParams.width = screenWidth.width
        mSplashFragmentImgAd.layoutParams.height = screenWidth.height * 1080 / 1334
        mTestCommonHeaderTitleView.setOnViewClickListener(this)
    }

    companion object {
        fun newInstance(): TabMainFragment {
            val fragment = TabMainFragment()
            return fragment
        }
    }

    override fun getSwipeBackEnable(): Boolean {
        return false
    }
    /**公共实现部分 end*/

    /**当前业务部分 start*/
    override fun onShowAd(adBean: SplashAdEntity) {
        ImageLoaderUtil.displayImage(adBean.imgPathUrl, mSplashFragmentImgAd as ImageView, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_RESOURCE, object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                mPresenter.checkSkip()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                mPresenter.startAdTime(adBean.showTime.toLong())
                return false
            }

        }, screenWidth.width, screenWidth.height * 1080 / 1334)
        RxView.clicks(mSplashFragmentImgAd).shieldDoubleClick {
            //点击广告
            mPresenter.stopTimeer()
            skipWeb(adBean.imgPageUrl)
        }
        RxView.clicks(mSplashFragmentTvSkip).shieldDoubleClick {
            mPresenter.checkSkip()
            baiDuUtils.start(object : BaiDuUtils.LocationCallBackListener {
                override fun onCallback(isSuccess: Boolean) {
                    Logger.i(isSuccess.toString())
                    Logger.i("经度=${baiDuUtils.longitudeStr} 纬度=${baiDuUtils.latitudeStr} city=${baiDuUtils.city} addrStr=${baiDuUtils.addrStr}")
                }
            })
        }
    }

    override fun onRight(view: View) {
        baiDuUtils.start(null)
    }
    override fun onLastRight(view: View) {
        start(TabMainFragment.newInstance())
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
    }

    override fun skipLogin() {
    }

    override fun skipWeb(webUrl: String) {
        NavigationUtil.navigationToWebShowResult(_mActivity, "http://youx7.youx.mobi/activity/qualitylife?appAreaCode=441723&appUserMobile=15868490449")
    }

    /**当前业务部分 end*/

}