
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View.VISIBLE
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import com.jakewharton.rxbinding2.view.RxView
import com.zhongjiang.hotel.base.ext.shieldDoubleClick
import com.zhongjiang.hotel.base.imageloader.ImageLoaderUtil
import com.zhongjiang.hotel.base.injection.WindowScreenInfo
import com.zhongjiang.hotel.base.ui.fragment.BaseMvpFragment
import com.zhongjiang.hotel.base.utils.PreferenceSettings
import com.zhongjiang.hotel.provider.db.SplashAdEntity
import com.zhongjiang.hotel.provider.event.ActivityRequestCode
import com.zhongjiang.hotel.provider.router.NavigationUtil
import com.zhongjiang.hotel.splash.R
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
        mPresenter.registerActivityResultEvent {
            when(it.requestCode){
                ActivityRequestCode.REQUEST_WEBSHOW_CODE.requestCode -> {
                    mPresenter.checkSkip()
                }
            }
        }
        PreferenceSettings.test = "55"
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_splash
    }

    override fun initView() {
        mSplashFragmentImgAd.layoutParams.width = screenWidth.width
        mSplashFragmentImgAd.layoutParams.height = screenWidth.height * 1080 / 1334
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
        }
    }

    override fun onRefreshTimer(str: String) {
        if (mSplashFragmentLlTimeer != null) {
            mSplashFragmentLlTimeer.visibility = VISIBLE
        }
        mSplashFragmentTvTime.text = str
    }

    override fun onLoginSuccess() {
        mActivity.finish()
        NavigationUtil.navigationToMain()
    }

    override fun skipLogin() {
        start(LoginFragment.newInstance(false), ISupportFragment.SINGLETASK)
    }

    override fun skipWeb(webUrl: String) {
        NavigationUtil.navigationToWebShowResult(mActivity,"http://youx7.youx.mobi/activity/qualitylife?appAreaCode=441723&appUserMobile=15868490449")
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).fitsSystemWindows(false).init()
    }
    /**当前业务部分 end*/

}