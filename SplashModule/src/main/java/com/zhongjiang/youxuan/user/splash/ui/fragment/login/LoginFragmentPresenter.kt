package com.zhongjiang.youxuan.user.splash.ui.fragment.login

import com.uber.autodispose.autoDisposable
import com.zhongjiang.youxuan.base.data.db.UserInfoEntity
import com.zhongjiang.youxuan.base.ext.excute
import com.zhongjiang.youxuan.base.presenter.BasePresenter
import com.zhongjiang.youxuan.base.rx.BaseMaybeObserver
import com.zhongjiang.youxuan.provider.common.CommonUtils
import com.zhongjiang.youxuan.user.splash.data.VerificationCodeResuleInfo
import com.zhongjiang.youxuan.user.splash.ui.SplashDataModel
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import javax.inject.Inject
import javax.inject.Singleton

class LoginFragmentPresenter @Inject constructor() : BasePresenter<LoginFragment, SplashDataModel>(), LoginFragmentContract.Presenter {

    @Inject
    @Singleton
    lateinit var commonUtils: CommonUtils
    @Inject
    lateinit var mModel:SplashDataModel

    override fun requestLogin(code: String, phoneStr: String, verificationCode: String) {
        mView.showLoading()
        mModel.requestLogin(code, phoneStr, verificationCode).excute(bindLifecycle(), object : BaseMaybeObserver<UserInfoEntity>(mView) {
            override fun onSuccess(t: UserInfoEntity) {
                super.onSuccess(t)
                commonUtils.setUserInfo(t)
                onLoginSuccess()
                mView.onLoginSuccess()
            }
        })
//        upFile(UpFileBean(UpFileBean.Companion.FileModuleType.ORDER_SECURITY,"/storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1553585976662.jpg")) {
//            com.orhanobut.logger.Logger.i("文件名 = ${it.fileName},上传状态= ${it.upType},上传进度= ${it.progress},图片路径 = ${it.filePath},上传返回路径=${it.upSuccessUrl}")
//        }
    }

    override fun requestVerificationCode(phoneStr: String) {
        mView.showLoading()
        mModel.requestVerificationCode(phoneStr).autoDisposable(bindLifecycle()).subscribe(object : BaseMaybeObserver<VerificationCodeResuleInfo>(mView) {
            override fun onSuccess(t: VerificationCodeResuleInfo) {
                super.onSuccess(t)
                mView.getVerificationCodeSuccess(t)
                if (t.status == 1) {
                    startVerificationCodeTimer()
                }

            }
        })
//        var upFileBean = UpFileBean(UpFileBean.Companion.FileModuleType.ORDER,"/storage/emulated/0/40529-666.dib")
//        var upFileBean1 = UpFileBean(UpFileBean.Companion.FileModuleType.ORDER_SECURITY,"/storage/emulated/0/40529-666.webp")
//        upFiles(arrayListOf(upFileBean1,upFileBean)) {
//            com.orhanobut.logger.Logger.i("文件名 = ${it.fileName},上传状态= ${it.upType},上传进度= ${it.progress},图片路径 = ${it.filePath},上传返回路径=${it.upSuccessUrl}")
//            if (it.upType ==2 || it.upType == 3){
//                mView.timerFinish()
//            }
//        }

    }

    fun startVerificationCodeTimer() {
        mModel.startTimer(60, Consumer { t ->
            mView.refreshVerificationCodeView(60.minus(t).toString())
        }, Action {
            mView.timerFinish()
        }).excute(bindBusLifecycle())
    }
}