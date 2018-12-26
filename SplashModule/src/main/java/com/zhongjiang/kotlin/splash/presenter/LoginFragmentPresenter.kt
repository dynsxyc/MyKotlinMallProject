package com.zhongjiang.kotlin.splash.presenter

import com.uber.autodispose.autoDisposable
import com.zhongjiang.kotlin.base.data.db.UserInfoEntity
import com.zhongjiang.kotlin.base.ext.excute
import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.base.rx.BaseMaybeObserver
import com.zhongjiang.kotlin.splash.data.VerificationCodeResuleInfo
import com.zhongjiang.kotlin.splash.presenter.contract.LoginFragmentContract
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import javax.inject.Inject

class LoginFragmentPresenter @Inject constructor(view: LoginFragmentContract.View, model: LoginFragmentContract.Model) : BasePresenter<LoginFragmentContract.View, LoginFragmentContract.Model>(view, model), LoginFragmentContract.Presenter {

    override fun requestLogin(code: String, phoneStr: String, verificationCode: String) {
        mView.showLoading()
        mModel.requestLogin(code, phoneStr, verificationCode).excute(bindLifecycle(),object : BaseMaybeObserver<UserInfoEntity>(mView) {
            override fun onSuccess(t: UserInfoEntity) {
                super.onSuccess(t)
                if (t !== null ) {
                    setUserInfo(t)
                }
                mView.loginSuccess()
            }
        })
    }

    override fun requestVerificationCode(phoneStr: String) {
        mView.showLoading()
        mModel.requestVerificationCode(phoneStr).autoDisposable(bindLifecycle()).subscribe(object : BaseMaybeObserver<VerificationCodeResuleInfo>(mView) {
            override fun onSuccess(t: VerificationCodeResuleInfo) {
                super.onSuccess(t)
                mView.getVerificationCodeSuccess(t)
                if (t.status == 1){
                    startVerificationCodeTimer()
                }

            }
        })
    }

    fun startVerificationCodeTimer() {
        mModel.startTimer(60, Consumer { t ->
            mView.refreshVerificationCodeView(60.minus(t).toString())
        }, Action {
            mView.timerFinish()
        }).autoDisposable(bindLifecycle()).subscribe()
    }
}