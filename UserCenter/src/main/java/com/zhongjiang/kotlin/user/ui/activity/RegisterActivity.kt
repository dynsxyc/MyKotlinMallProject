package com.zhongjiang.kotlin.user.ui.activity

import android.view.View
import android.view.View.OnClickListener
import com.zhongjiang.kotlin.base.common.AppManager
import com.zhongjiang.kotlin.base.ext.OnClick
import com.zhongjiang.kotlin.base.ext.enable
import com.zhongjiang.kotlin.base.ui.activity.BaseMvpActivity
import com.zhongjiang.kotlin.user.R
import com.zhongjiang.kotlin.user.injection.component.DaggerUserComponent
import com.zhongjiang.kotlin.user.presenter.RegistenerPresenter
import com.zhongjiang.kotlin.user.presenter.view.RegistenerView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseMvpActivity<RegistenerPresenter>(), RegistenerView , OnClickListener{
    override fun loadThisData() {
    }

    override fun getThisContentLayoutRes(): Int {
        return R.layout.activity_register
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }
    /*
    * 注册回调
    * */
    override fun onRegisterResult(result: String) {
        toast(result)
    }

    /*
    * 初始化视图
    * */
    override fun initThisView() {
        mRegisterBtn.enable(mMobileEt,{isBtnEnable()})
        mRegisterBtn.enable(mVerifyCodeEt,{isBtnEnable()})
        mRegisterBtn.enable(mPwdEt,{isBtnEnable()})
        mRegisterBtn.enable(mPwdConfirmEt,{isBtnEnable()})
        mRegisterBtn.OnClick (this)
        mVerifyCodeBtn.OnClick(this)
    }

    private var pressTime:Long = 0
    override fun onBackPressed() {
        super.onBackPressed()
        var time = System.currentTimeMillis();
        if (time -pressTime>2000){
            toast("再按一次退出程序")
            pressTime = time
        }else{
            AppManager.instance.exitApp(this)
        }
    }

    override fun onClick(view: View) {
    when(view.id){
        R.id.mVerifyCodeBtn ->{
//                mVerifyCodeBtn.requestSendVerifyNumber()
            mPresenter.login(mMobileEt.text.toString(), mVerifyCodeEt.text.toString(), mPwdEt.text.toString())
            toast("发送验证码成功")
        }
        R.id.mRegisterBtn ->{
            mPresenter.rigister(mMobileEt.text.toString(), mVerifyCodeEt.text.toString(), mPwdEt.text.toString())
        }
    }

    }

    fun isBtnEnable() : Boolean{
        return mMobileEt.text.isNullOrEmpty().not() and
                mVerifyCodeEt.text.isNullOrEmpty().not() and
                mPwdEt.text.isNullOrEmpty().not() and
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }

}
