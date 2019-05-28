package com.zhongjiang.youxuan.provider.common

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.zhongjiang.youxuan.base.data.db.UserInfoEntity
import io.objectbox.Box
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

class CommonUtils @Inject constructor() {

    @Inject
    @Singleton
    lateinit var mUserInfoEntity: Box<UserInfoEntity>

    /**
     * 退出应用程序
     */
    fun appExit(context: Context) = try {
        val i = Intent(Intent.ACTION_MAIN)
        i.addCategory(Intent.CATEGORY_HOME)
        context.startActivity(i)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    /**判断用户是否登录*/
    fun isUserLogin(): Boolean {
        if (mUserInfoEntity.count() <= 0) {
            return false
        }
        var userInfo = mUserInfoEntity.all[0]
        return !userInfo.ticket.isEmpty()
    }

    /**获取用户信息*/
    fun getUserInfo(): UserInfoEntity {
        return mUserInfoEntity.all[0]
    }

    /**更新/添加用户信息*/
    fun setUserInfo(userInfoEntity: UserInfoEntity) {
        if (mUserInfoEntity.count() <= 0) {
            mUserInfoEntity.put(userInfoEntity)
        } else {
            var bean = mUserInfoEntity.all[0]
            bean.clone(userInfoEntity)
            mUserInfoEntity.put(bean)
        }
    }

    /**退出登录 移除用户信息*/
    fun removeUserInfo() {
        mUserInfoEntity.removeAll()
    }

    private var patternMobile = Pattern.compile("^1\\d{10}$")

    /**
     * 判断输入的手机号是否合法 true 合法 false 不合法
     */
    fun isMobile(mobiles: String): Boolean {
        if (TextUtils.isEmpty(mobiles)) {
            return false
        }
        val m = patternMobile.matcher(mobiles.replace(" ".toRegex(), ""))
        return m.matches()
    }
}