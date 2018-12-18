package com.zhongjiang.kotlin.user.ui.activity

import android.view.View
import com.zhongjiang.kotlin.base.ext.OnClick
import com.zhongjiang.kotlin.base.ext.enable
import com.zhongjiang.kotlin.base.ui.activity.BaseMvpActivity
import com.zhongjiang.kotlin.user.R
import com.zhongjiang.kotlin.user.injection.component.DaggerUserComponent
import com.zhongjiang.kotlin.user.presenter.ForgetPwdPresenter
import com.zhongjiang.kotlin.user.presenter.view.ForgetPwdView
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class ForgetPwdActivity : BaseMvpActivity<ForgetPwdPresenter>(), ForgetPwdView, View.OnClickListener {
    override fun getThisContentLayoutRes(): Int {
        return R.layout.activity_forget_pwd
    }

    override fun loadThisData() {
    }

    override fun initThisView() {
        mNextBtn.enable(mMobileEt,{isBtnEnable()})
        mNextBtn.enable(mVerifyCodeEt,{isBtnEnable()})
        mNextBtn.OnClick (this)
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(baseActivityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onClick(view: View) {
    when(view.id){
        R.id.mVerifyCodeBtn ->{
            toast("发送验证码成功")
            mVerifyCodeBtn.requestSendVerifyNumber()
        }
        R.id.mNextBtn ->{
            mPresenter.forgetPwd(mMobileEt.text.toString(), mVerifyCodeEt.text.toString())
        }
    }

    }

    fun isBtnEnable() : Boolean{
        return mMobileEt.text.isNullOrEmpty().not() and
                mVerifyCodeEt.text.isNullOrEmpty().not()
    }

    override fun onForgetPwdResult(result: String) {
        toast(result)
        startActivity(intentFor<ResetPwdActivity>("mobile" to  mMobileEt.text.toString()))
    }

}