package com.zhongjiang.kotlin.splash.ui.fragment

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
import com.zhongjiang.kotlin.splash.presenter.SplashPresenter
import com.zhongjiang.kotlin.splash.presenter.contract.SplashContract
import kotlinx.android.synthetic.main.fragment_splash.*
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/25.
 */
class SplashFragment : BaseMvpFragment<SplashPresenter>(), SplashContract.View {

    override fun initData() {
        mPresenter.requestAdInfo("")
    }

    override fun getLayoutRes(): Int {
        return  R.layout.fragment_splash
    }

    override fun onShowAd(adImgPathUrl: String) {
        ImageLoaderUtil.displayImage(adImgPathUrl, mYxImageView, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_RESOURCE, object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                return false;
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false;
            }

        }, 0, 0)
        RxView.clicks(mYxImageView)
                .throttleFirst(800, TimeUnit.MILLISECONDS)
                .subscribe{
                    activity!!.toast("测试点击")
                    Log.i("test1","-------------")
                };
    }
    @Inject
    lateinit var screenWidth:WindowScreenInfo

    companion object {
        fun newInstance(): SplashFragment {
            val args = Bundle()
            val fragment = SplashFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView(){
        mYxImageView.layoutParams.width = screenWidth.width
        mYxImageView.layoutParams.height = screenWidth.height* 1080 / 1334
    }

    override fun getSwipeBackEnable():Boolean{
        return false
    }

}