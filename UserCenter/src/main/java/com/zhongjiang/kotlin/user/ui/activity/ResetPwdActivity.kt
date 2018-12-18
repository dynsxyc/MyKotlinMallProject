package com.zhongjiang.kotlin.user.ui.activity

import com.zhongjiang.kotlin.base.ext.OnClick
import com.zhongjiang.kotlin.base.ext.enable
import com.zhongjiang.kotlin.base.ui.activity.BaseMvpActivity
import com.zhongjiang.kotlin.user.R
import com.zhongjiang.kotlin.user.injection.component.DaggerUserComponent
import com.zhongjiang.kotlin.user.presenter.ResetPwdPresenter
import com.zhongjiang.kotlin.user.presenter.view.ResetPwdView
import kotlinx.android.synthetic.main.activity_reset_pwd.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.toast

class ResetPwdActivity : BaseMvpActivity<ResetPwdPresenter>(), ResetPwdView{
    override fun getThisContentLayoutRes(): Int {
        return R.layout.activity_reset_pwd
    }

    override fun loadThisData() {
    }

    override fun initThisView() {
        mConfirmBtn.enable(mPwdEt,{isBtnEnable()})
        mConfirmBtn.enable(mPwdConfirmEt,{isBtnEnable()})
        mConfirmBtn.OnClick {
            if (mPwdEt.text.toString() != mPwdConfirmEt.text.toString()){
                toast("密码不一致")
                return@OnClick
            }
            mPresenter.resetPwd(intent.getStringExtra("mobile"),mPwdConfirmEt.text.toString())
        }
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(baseActivityComponent).build().inject(this)
        mPresenter.mView = this
    }

    fun isBtnEnable() : Boolean{
        return mPwdEt.text.isNullOrEmpty().not() and
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }
    override fun onResetPwdResult(result: String) {
        toast(result)
        startActivity(intentFor<LoginActivity>().singleTop().clearTop())
    }
}