package com.zhongjiang.kotlin.splash.ui.fragment

import com.jakewharton.rxbinding2.view.RxView
import com.zhongjiang.kotlin.base.busevent.ActivityRequestCode
import com.zhongjiang.kotlin.base.ext.shieldDoubleClick
import com.zhongjiang.kotlin.base.oss.UpFileBean
import com.zhongjiang.kotlin.base.ui.fragment.BaseSelectorImgFragment
import com.zhongjiang.kotlin.splash.R
import com.zhongjiang.kotlin.splash.presenter.loginfragment.TestFragmentContract
import com.zhongjiang.kotlin.splash.presenter.loginfragment.TestFragmentPresenter
import kotlinx.android.synthetic.main.fragment_testpicture.*

class TestPictureFragment : BaseSelectorImgFragment<TestFragmentPresenter,TestFragmentContract.View,TestFragmentContract.Model>(),TestFragmentContract.View {

    override fun getRequestSelectorImgCode(): Int {
        return ActivityRequestCode.REQUEST_IMAGESELECTOR_CODE.requestCode
    }

    override fun getDefaultMaxSelectNum(): Int {
        return 9
    }

    override fun getDefaultFileModuleType(): UpFileBean.Companion.FileModuleType {
        return UpFileBean.Companion.FileModuleType.ANDROID
    }

    override fun onFileUpIng(it: UpFileBean) {
        com.orhanobut.logger.Logger.i("文件名 = ${it.fileName},上传状态= ${it.upType},上传进度= ${it.progress},图片路径 = ${it.filePath},上传返回路径=${it.upSuccessUrl}")
    }

    override fun initView() {
        RxView.clicks(btCamera).shieldDoubleClick {
            openMedia(true)
        }
        RxView.clicks(btAlbum).shieldDoubleClick {
            openMedia(false)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_testpicture
    }
}
