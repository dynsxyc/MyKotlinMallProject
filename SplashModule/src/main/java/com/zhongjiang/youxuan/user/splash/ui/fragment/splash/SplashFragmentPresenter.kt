package com.zhongjiang.youxuan.user.splash.ui.fragment.splash

import android.util.Log
import com.zhongjiang.youxuan.base.data.db.SplashAdEntity
import com.zhongjiang.youxuan.base.ext.excute
import com.zhongjiang.youxuan.base.ui.basemvp.BasePresenter
import com.zhongjiang.youxuan.base.rx.BaseMaybeObserver
import com.zhongjiang.hotel.provider.common.CommonUtils
import com.zhongjiang.youxuan.user.splash.ui.SplashDataModel
import io.objectbox.Box
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.rx_cache2.Reply
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by dyn on 2018/7/25.
 */
class SplashFragmentPresenter @Inject constructor() : BasePresenter<SplashFragment, SplashDataModel>(), SplashFragmentContract.Presenter {
    @Inject
    lateinit var adInfoBox: Box<SplashAdEntity>

    @Inject
    @Singleton
    lateinit var commonUtils: CommonUtils

    override fun requestAdInfo(name: String) {
        Log.w("test","--------------adInfoBox = $adInfoBox")
        Log.w("test","--------------mModel = $mModel")
        mModel.requestAdInfo(name,true).excute(bindLifecycle(),object : BaseMaybeObserver<Reply<List<SplashAdEntity>>>(mView) {
            override fun onSuccess(t: Reply<List<SplashAdEntity>>) {
                super.onSuccess(t)
                System.out.print(t)
                if (t.data.isEmpty()) {
                    adInfoBox.removeAll()
                } else
                    if (adInfoBox.count() <= 0) {
                        adInfoBox.put(t.data)
                    } else {
                        var adBean = adInfoBox.all[0]
                        adBean.clone(t.data.get(0))
                        adInfoBox.put(adBean)
                    }
            }

            override fun onError(e: Throwable) {
                adInfoBox.removeAll()
            }

        })
        if (adInfoBox.count() > 0) {
            var adBean = adInfoBox.all[0]
            if (adBean.imgPathUrl.isNotEmpty()) {
                mView.onShowAd(adBean)
            }
        } else {
            startTimer(2, Consumer {
            }, Action {
                checkSkip()
            })
        }
        checkPermissions()

    }

    fun startAdTime(long: Long) {
        startTimer(long.plus(1), Consumer { t ->
            mView.onRefreshTimer(long.minus(t).toString())
            Log.i("test1", "onTimer ${long.minus(t)}")
        }, Action {
            checkSkip()
            Log.i("test1", "onTimer 结束")
        })
    }


    override fun checkSkip() {
        if (commonUtils.isUserLogin()) {
            onLoginSuccess()
            mView.onLoginSuccess()
        } else {
            mView.skipLogin()
        }
    }

    override fun checkPermissions(): Boolean {
//        RxPermissions(mContent).requestEach(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe {
//            if (it.granted) {
//                //同意
//            } else if (it.shouldShowRequestPermissionRationale) {
//                //拒绝权限，不再问任何问题
//            } else {
//                //拒绝权限再问一次
//                //需要转到设置
//            }
//        }
        return false
    }


}