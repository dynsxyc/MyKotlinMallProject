package com.zhongjiang.kotlin.splash.presenter.loginfragment

import com.ihsanbal.logging.Logger
import com.uber.autodispose.autoDisposable
import com.zhongjiang.kotlin.base.data.db.UserInfoEntity
import com.zhongjiang.kotlin.base.ext.excute
import com.zhongjiang.kotlin.base.oss.OssService
import com.zhongjiang.kotlin.base.oss.UpFileBean
import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.base.rx.BaseMaybeObserver
import com.zhongjiang.kotlin.provider.common.CommonUtils
import com.zhongjiang.kotlin.splash.data.VerificationCodeResuleInfo
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

class LoginFragmentPresenter @Inject constructor(view: LoginFragmentContract.View, model: LoginFragmentContract.Model) : BasePresenter<LoginFragmentContract.View, LoginFragmentContract.Model>(view, model), LoginFragmentContract.Presenter {

    @field:Named("public")
    @Inject
    lateinit var publicOssService: OssService
    @Inject
    @Singleton
    lateinit var commonUtils: CommonUtils

    override fun requestLogin(code: String, phoneStr: String, verificationCode: String) {
        mView.showLoading()
        mModel.requestLogin(code, phoneStr, verificationCode).excute(bindLifecycle(), object : BaseMaybeObserver<UserInfoEntity>(mView) {
            override fun onSuccess(t: UserInfoEntity) {
                super.onSuccess(t)
                commonUtils.setUserInfo(t)
                onLoginSuccess()
                mView.onLoginSuccess();
            }
        })
    }

    override fun requestVerificationCode(phoneStr: String) {
//        mView.showLoading()
//        mModel.requestVerificationCode(phoneStr).autoDisposable(bindLifecycle()).subscribe(object : BaseMaybeObserver<VerificationCodeResuleInfo>(mView) {
//            override fun onSuccess(t: VerificationCodeResuleInfo) {
//                super.onSuccess(t)
//                mView.getVerificationCodeSuccess(t)
//                if (t.status == 1) {
//                    startVerificationCodeTimer()
//                }
//
//            }
//        })

        publicOssService.asyncPutFile(arrayListOf(UpFileBean("Android test.png","/storage/0123-4567/busihall/1983252ec9e7b76dd2da4be0798666b1.gif",0)),bindLifecycle(),{
            com.orhanobut.logger.Logger.i(it.progress.toString())
        },{
            com.orhanobut.logger.Logger.i("onsuccess")
        },{
            com.orhanobut.logger.Logger.i("onfail")
        }).subscribe()
    }

    fun startVerificationCodeTimer() {
        mModel.startTimer(60, Consumer { t ->
            mView.refreshVerificationCodeView(60.minus(t).toString())
        }, Action {
            mView.timerFinish()
        }).excute(bindBusLifecycle())
    }
}