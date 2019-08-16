package com.zhongjiang.kotlin.splash.ui.fragment

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.BounceInterpolator
import androidx.core.content.ContextCompat
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import com.jakewharton.rxbinding2.view.RxView
import com.zhongjiang.kotlin.splash.data.VerificationCodeResuleInfo
import com.zhongjiang.kotlin.splash.presenter.loginfragment.LoginFragmentContract
import com.zhongjiang.kotlin.splash.presenter.loginfragment.LoginFragmentPresenter
import com.zhongjiang.youxuan.base.ext.editEnable
import com.zhongjiang.youxuan.base.ext.padLeft
import com.zhongjiang.youxuan.base.ext.setVisible
import com.zhongjiang.youxuan.base.ext.shieldDoubleClick
import com.zhongjiang.youxuan.base.ui.fragment.BaseMvpFragment
import com.zhongjiang.youxuan.base.utils.FromatPhoneTextWatcher
import com.zhongjiang.youxuan.provider.router.NavigationUtil
import com.zhongjiang.youxuan.user.splash.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.viewsub_login_video.*
import org.jetbrains.anko.toast

class LoginFragment : BaseMvpFragment<LoginFragmentPresenter>(), LoginFragmentContract.View {
    override fun onLoginSuccess() {
        _mActivity.finish()
        NavigationUtil.navigationToMain()
    }

    /**公共实现部分 start*/
    var mSingleCode: String = ""
    var isToLogin: Boolean = false

    companion object {
        fun newInstance(isToLogin: Boolean): LoginFragment {
            val args = Bundle()
            val fragment = LoginFragment()
            args.putBoolean("isToLogin", isToLogin)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        mLoginFragmentVideoView.setVideoURI(Uri.parse("android.resource://" + _mActivity.getPackageName() + "/" + R.raw.splash_video))
        mLoginFragmentVideoView.setOnCompletionListener { mLoginFragmentVideoView.start() }
        mLoginFragmentVideoView.setOnPreparedListener { mp ->
            mp.setVolume(0f, 0f)
            mp.start()
        }
        RxView.clicks(mLoginFragmentRoundTvSkip).shieldDoubleClick {
//            showLoginView()
            start(TestPictureFragment())
        }
    }

    override fun initData() {
        isToLogin = arguments!!.getBoolean("isToLogin")
        if (isToLogin) {
            showLoginView()
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun getSwipeBackEnable(): Boolean {
        return false
    }

    override fun onResume() {
        super.onResume()
        mLoginFragmentVideoView.resume()
    }
    override fun onPause() {
        super.onPause()
        mLoginFragmentVideoView.pause()
    }

    override fun onBackPressedSupport(): Boolean {
        mPresenter.commonUtils.appExit(_mActivity)
        return true
    }

    fun initSubView() {
        mLoginFragmentEtPhone.addTextChangedListener(FromatPhoneTextWatcher(mLoginFragmentEtPhone) { checkLoginEnable() })
        mLoginFragmentEtPhone.requestFocus()
        mLoginFragmentRoundTvLogin.editEnable(mLoginFragmentEtVerificationCode) { checkLoginEnable() }

        RxView.clicks(mLoginFragmentRoundTvGetVerificationCode).shieldDoubleClick {
            //            获取验证码
            val phoneNumber = mLoginFragmentEtPhone.text.toString().replace(" ", "")
            mPresenter.requestVerificationCode(phoneNumber)
        }
        RxView.clicks(mLoginFragmentTvServerAgreement).shieldDoubleClick {
            //服务协议
            NavigationUtil.navigationToWebShow("http://www.baidu.com")
//            WXPayUtils.startPay(_mActivity,"wx72a9d8637eb96ea1","1341772801","wx22171503762155c7a9eaeb523173771940","Sign=WXPay","vqoMUlYsJgSkykqg","1553346066","Tsou1234567891234567891234567890")
//            AliPayUtils.startPay(_mActivity,"partner=\"2088221618455679\"&seller_id=\"caiwu@youx.mobi\"&out_trade_no=\"1109046297907691520\"&subject=\"YOU选订单支付\"&body=\"YOU选订单支付-1109046297907691520\"&total_fee=\"0.01\"&notify_url=\"http://testapi.you-x.cn/pay/alipay/payCallBack\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"30m\"&return_url=\"m.alipay.com\"&sign=\"QICVWq%2BWrl2J8Rf%2B5efj%2BFsWOWNDaCA59D%2ByJKtw6vq7TScenKfhJOnzWRc3lbJ9o0A2gkgwSkytZM537pW6ws1foEF7eVRcdNFPP9cG%2FPjGtnSGGq6kFgREnffuM7IWJ%2BcQ8FMltx5zEFHOoSFhf8P3Til6kT9IAYCfqjrXK%2Bs%3D\"&sign_type=\"RSA\"",schedulers
//            ) { isOk, s ->
//                _mActivity.toast(s)
//            }
        }
        RxView.clicks(mLoginFragmentRoundTvLogin).shieldDoubleClick {
            //登录
            val phoneNumber = mLoginFragmentEtPhone.text.toString().replace(" ", "")
            mPresenter.requestLogin(mSingleCode, phoneNumber, mLoginFragmentEtVerificationCode.text.toString())
        }
    }
    /**公共实现部分 end*/


    /**
     * 显示登录内容
     */
    fun showLoginView() {
        _mActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        ImmersionBar.with(this).hideBar(BarHide.FLAG_SHOW_BAR).keyboardEnable(true).autoDarkModeEnable(false).statusBarColorTransform(R.color.black).statusBarAlpha(0.2f).init()
        mLoginFragmentViewSub.inflate()
        initSubView()
        mLoginFragmentRoundTvSkip.animate().alpha(0f).translationY(500f).setDuration(500).start()
        mLoginFragmentTvCompanyCatchword.animate().alpha(0f).translationY(300f).setDuration(500).setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                mLoginFragmentRoundTvSkip.setClickable(false)
                startLoginContentAnimate()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        }).start()

    }

    /**
     * 显示登录页动画
     */
    fun startLoginContentAnimate() {
        val phoneAlphaAn = ObjectAnimator.ofFloat(mLoginFragmentLlPhone, "alpha", 0f, 1f)
        val logoAlphaAn = ObjectAnimator.ofFloat(mLoginFragmentImgLogo, "alpha", 0f, 1f)
        val codeAlphaAn = ObjectAnimator.ofFloat(mLoginFragmentLlCode, "alpha", 0f, 1f)
        val btLoginAlphaAn = ObjectAnimator.ofFloat(mLoginFragmentRoundTvLogin, "alpha", 0f, 1f)
        val llServerAgreementAlphaAn = ObjectAnimator.ofFloat(mLoginFragmentLlAgreement, "alpha", 0f, 1f)

        val phoneTyAn = ObjectAnimator.ofFloat(mLoginFragmentLlPhone, "translationY", 100f, 0f)
        val codeTyAn = ObjectAnimator.ofFloat(mLoginFragmentLlCode, "translationY", 300f, 0f)
        val btLoginTyAn = ObjectAnimator.ofFloat(mLoginFragmentRoundTvLogin, "translationY", 500f, 1f)
        val llServerAgreementTyAn = ObjectAnimator.ofFloat(mLoginFragmentLlAgreement, "translationY", 200f, 0f)

        logoAlphaAn.setDuration(800)
        logoAlphaAn.start()
        val phoneSet = AnimatorSet()
        phoneSet.duration = 300
        phoneSet.interpolator = BounceInterpolator()
        phoneSet.playTogether(phoneAlphaAn, phoneTyAn)
        phoneSet.start()
        val codeSet = AnimatorSet()
        codeSet.duration = 500
        codeSet.interpolator = BounceInterpolator()
        codeSet.playTogether(codeAlphaAn, codeTyAn)
        codeSet.start()
        val btSet = AnimatorSet()
        btSet.duration = 800
        btSet.interpolator = BounceInterpolator()
        btSet.playTogether(btLoginAlphaAn, btLoginTyAn)
        btSet.start()
        val llServerAgreementSet = AnimatorSet()
        llServerAgreementSet.duration = 300
        llServerAgreementSet.interpolator = BounceInterpolator()
        llServerAgreementSet.playTogether(llServerAgreementAlphaAn, llServerAgreementTyAn)
        llServerAgreementSet.start()
    }

    fun checkLoginEnable(): Boolean {
        var phoneStatus = mLoginFragmentEtPhone.text.isNullOrEmpty().not() and mPresenter.commonUtils.isMobile(mLoginFragmentEtPhone.text.toString())
        var codeStatus = mLoginFragmentEtVerificationCode.text.isNullOrEmpty().not()
        var result = phoneStatus and codeStatus

        if (result) {
            mLoginFragmentRoundTvLogin.delegate.backgroundColor = ContextCompat.getColor(_mActivity,R.color.common_red)
        } else {
            mLoginFragmentRoundTvLogin.delegate.backgroundColor = Color.parseColor("#99dc2828")
        }
        mLoginFragmentRoundTvGetVerificationCode.setVisible(phoneStatus)
        return result
    }

    override fun refreshVerificationCodeView(long: String) {
        mLoginFragmentRoundTvGetVerificationCode.text = "$long 秒后重发"
        mLoginFragmentRoundTvGetVerificationCode.isClickable = false
        mLoginFragmentEtPhone.isEnabled = false
    }

    override fun timerFinish() {
        mLoginFragmentRoundTvGetVerificationCode.text = "重新发送"
        mLoginFragmentRoundTvGetVerificationCode.isClickable = true
        mLoginFragmentEtPhone.isEnabled = true
    }

    override fun getVerificationCodeSuccess(t: VerificationCodeResuleInfo) {
        mSingleCode = t.code
        context!!.toast(t.showMessage)
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init()
    }

}