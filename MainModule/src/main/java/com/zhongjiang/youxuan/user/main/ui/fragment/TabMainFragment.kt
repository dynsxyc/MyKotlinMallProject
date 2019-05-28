package com.zhongjiang.kotlin.splash.ui.fragment

import android.view.View
import com.zhongjiang.kotlin.splash.presenter.splashfragment.TabMainFragmentContract
import com.zhongjiang.kotlin.splash.presenter.splashfragment.TabMainFragmentPresenter
import com.zhongjiang.youxuan.base.injection.WindowScreenInfo
import com.zhongjiang.youxuan.base.ui.fragment.BaseMvpFragment
import com.zhongjiang.youxuan.provider.router.NavigationUtil
import com.zhongjiang.youxuan.user.main.R
import kotlinx.android.synthetic.main.fragment_basehome.*
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/25.
 */
class TabMainFragment : BaseMvpFragment<TabMainFragmentPresenter>(), TabMainFragmentContract.View {
    @Inject
    lateinit var screenWidth: WindowScreenInfo

    /**公共实现部分 start*/
    override fun initData() {
//        mPresenter.requestAdInfo("")
//        mPresenter.registerActivityResultEvent {
//            when (it.requestCoder) {
//                ActivityRequestCode.REQUEST_WEBSHOW_CODE.requestCode -> {
//                    mPresenter.checkSkip()
//                }
//            }
//        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_basehome
    }

    override fun initView() {
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
//    override fun onShowAd(adBean: SplashAdEntity) {
//        ImageLoaderUtil.displayImage(adBean.imgPathUrl, mSplashFragmentImgAd as ImageView, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_RESOURCE, object : RequestListener<Drawable> {
//            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
//                mPresenter.checkSkip()
//                return false
//            }
//
//            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                mPresenter.startAdTime(adBean.showTime.toLong())
//                return false
//            }
//
//        }, screenWidth.width, screenWidth.height * 1080 / 1334)
//        RxView.clicks(mSplashFragmentImgAd).shieldDoubleClick {
//            //点击广告
//            mPresenter.stopTimeer()
//            skipWeb(adBean.imgPageUrl)
//        }
//        RxView.clicks(mSplashFragmentTvSkip).shieldDoubleClick {
//            mPresenter.checkSkip()
//        }
//    }

    override fun onRight(view: View) {
        mPresenter.startLocation(_mActivity)
    }
    override fun onLastRight(view: View) {
        start(TestPictureFragment())
    }

    fun skipWeb(webUrl: String) {
        NavigationUtil.navigationToWebShowResult(_mActivity, "http://youx7.youx.mobi/activity/qualitylife?appAreaCode=441723&appUserMobile=15868490449")
    }
    /**当前业务部分 end*/

}