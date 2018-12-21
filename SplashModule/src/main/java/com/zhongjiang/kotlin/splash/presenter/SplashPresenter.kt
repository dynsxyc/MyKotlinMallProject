package com.zhongjiang.kotlin.splash.presenter

import android.Manifest
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhongjiang.kotlin.base.data.db.SplashAdBean
import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.base.rx.BaseMaybeObserver
import com.zhongjiang.kotlin.splash.presenter.contract.SplashContract
import com.zhongjiang.kotlin.splash.ui.fragment.SplashFragment
import io.objectbox.Box
import io.reactivex.Maybe
import io.reactivex.functions.Function
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/25.
 */
class SplashPresenter @Inject constructor(view: SplashContract.View, model: SplashContract.Model) : BasePresenter<SplashContract.View, SplashContract.Model>(view, model), SplashContract.Presenter {
    override fun checkPermissions(): Boolean {
        RxPermissions(mContent).requestEach(Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe{
            if (it.granted){
                //同意
            }else if (it.shouldShowRequestPermissionRationale){
                //拒绝权限，不再问任何问题
            }else{
                //拒绝权限再问一次
                //需要转到设置
            }
        }
        return false
    }


    @Inject
    lateinit var adInfoBox: Box<SplashAdBean>

    @Inject
    lateinit var mContent:SplashFragment
    override fun requestAdInfo(name: String) {
        mModel.requestAdInfo(name).flatMap(Function <List<SplashAdBean>, Maybe<SplashAdBean>>{
            if (it.isEmpty()){
                return@Function Maybe.error(NullPointerException())
            }else{
                return@Function Maybe.just(it[0])
            }
        }).`as`(bindLifecycle()).subscribe(object : BaseMaybeObserver<SplashAdBean>(mView) {
            override fun onSuccess(t: SplashAdBean) {
                if (t !== null) {
                    if (adInfoBox.count() <= 0) {
                        adInfoBox.put(t)
                    } else {
                        var adBean = adInfoBox.all[0]
                        adBean.clone(t)
                        adInfoBox.put(adBean)
                    }
                }else{
                    adInfoBox.removeAll()
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                adInfoBox.removeAll()
            }
        })

        if (adInfoBox.count() > 0) {
            var adBean = adInfoBox.all[0]
            if (adBean.imgPathUrl.isNotEmpty()) {
                mView.onShowAd(adBean.imgPathUrl)
            }
        }
        checkPermissions()
    }



}