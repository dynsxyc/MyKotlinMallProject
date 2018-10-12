package com.zhongjiang.kotlin.user.ui.activity

import android.view.View
import com.zhongjiang.kotlin.base.ext.OnClick
import com.zhongjiang.kotlin.base.ext.enable
import com.zhongjiang.kotlin.base.ui.activity.BaseMvpActivity
import com.zhongjiang.kotlin.user.R
import com.zhongjiang.kotlin.user.data.protocol.UserInfo
import com.zhongjiang.kotlin.user.injection.component.DaggerUserComponent
import com.zhongjiang.kotlin.user.presenter.LoginPresenter
import com.zhongjiang.kotlin.user.presenter.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {


    override fun getThisContentLayoutRes(): Int {
        return R.layout.activity_login
    }
    override fun loadThisData() {
    }

    override fun initThisView() {
        mLoginBtn.enable(mMobileEt,{isBtnEnable()})
        mLoginBtn.enable(mPwdEt,this::isBtnEnable)
        mLoginBtn.OnClick (this)
        mForgetPwdTv.OnClick (this)
        mHeaderBar.getRightTv().OnClick(this)
    }


    /*
    * 登录回调
    * */
    override fun onLoginResult(result: UserInfo) {
        toast("登录成功")
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onClick(view: View) {
    when(view.id){
        R.id.mLoginBtn ->{
//            mPresenter.login(mMobileEt.text.toString(),mPwdEt.text.toString(),"")
            startActivity<UserInfoActivity>()
        }
        R.id.mRightTv ->{
            startActivity<RegisterActivity>()
        }
        R.id.mForgetPwdTv ->{
            startActivity<ForgetPwdActivity>()
        }
    }

    }

    fun isBtnEnable() : Boolean{
        return mMobileEt.text.isNullOrEmpty().not() and
                mPwdEt.text.isNullOrEmpty().not()
    }

}