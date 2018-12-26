package com.zhongjiang.kotlin.base.presenter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.ScopeProvider
import com.zhongjiang.kotlin.base.NetWorkUtils
import com.zhongjiang.kotlin.base.common.BaseApplication
import com.zhongjiang.kotlin.base.data.db.UserInfoEntity
import com.zhongjiang.kotlin.base.utils.RxLifecycleUtils
import io.objectbox.Box
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/13.
 */
open class BasePresenter<V : IView,M:IModel>  constructor(view:V,model:M): IPresenter {

    //    1. lateinit 延迟加载
//    2.lateinit 只能修饰, 非kotlin基本类型
//    因为Kotlin会使用null来对每一个用lateinit修饰的属性做初始化，而基础类型是没有null类型，所以无法使用lateinit。
    var mView = view

    var mModel = model
    @Inject
    lateinit var context: BaseApplication

    @Inject
    lateinit var mUserInfoEntity: Box<UserInfoEntity>

    fun checkNetWork(): Boolean {
        if (NetWorkUtils.isNetWorkAvailable(context)) {
            return true
        }
        mView.onError("网络不可用")
        return false
    }

    lateinit var lifecycleProvider: LifecycleOwner

    fun  bindLifecycle(): ScopeProvider{
        return RxLifecycleUtils.bindLifecycle(lifecycleProvider)
    }

    override fun setLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        this.lifecycleProvider = lifecycleOwner
    }
    @MainThread @CallSuper
    override fun onCreate(owner: LifecycleOwner) {
    }
    @MainThread @CallSuper
    override fun onStart(owner: LifecycleOwner) {
    }
    @MainThread @CallSuper
    override fun onResume(owner: LifecycleOwner) {
    }
    @MainThread @CallSuper
    override fun onPause(owner: LifecycleOwner) {
    }

    @MainThread @CallSuper
    override fun onStop(owner: LifecycleOwner) {
    }

    @CallSuper
    @MainThread
    override fun onDestroy(owner: LifecycleOwner) {
            mModel.onDestroy()
    }

    @CallSuper
    @MainThread
    override fun onLifecycleChanged(owner: LifecycleOwner) {
    }

    /**
     * 退出应用程序
     */
    fun appExit(context: Context) {
        try {
            val i = Intent(Intent.ACTION_MAIN)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            i.addCategory(Intent.CATEGORY_HOME)
            context.startActivity(i)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    /**判断用户是否登录*/
    fun isUserLogin() : Boolean {
        if (mUserInfoEntity.count() <= 0){
            return false
        }
        var userInfo = mUserInfoEntity.all[0]
        return !userInfo.ticket.isEmpty()
    }
    /**获取用户信息*/
    fun getUserInfo() : UserInfoEntity {
        return mUserInfoEntity.all[0]
    }
    /**更新/添加用户信息*/
    fun setUserInfo(userInfoEntity: UserInfoEntity){
        if (mUserInfoEntity.count() <= 0) {
            mUserInfoEntity.put(userInfoEntity)
        } else {
            var bean = mUserInfoEntity.all[0]
            bean.clone(userInfoEntity)
            mUserInfoEntity.put(bean)
        }
    }
    /**退出登录 移除用户信息*/
    fun removeUserInfo(){
        mUserInfoEntity.removeAll()
    }

    var PATTERN_MOBILE = Pattern.compile("^1\\d{10}$")

    /**
     * 判断输入的手机号是否合法 true 合法 false 不合法
     */
    fun isMobileNO(mobiles: String): Boolean {
        var mobiles = mobiles
        if (TextUtils.isEmpty(mobiles)) {
            return false
        }
        mobiles = mobiles.replace(" ".toRegex(), "")
        val m = PATTERN_MOBILE.matcher(mobiles)
        return m.matches()
    }

}